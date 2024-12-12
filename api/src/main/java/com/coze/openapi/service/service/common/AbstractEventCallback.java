package com.coze.openapi.service.service.common;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.exception.CozeApiExcetion;
import com.coze.openapi.client.exception.CozeError;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.FlowableEmitter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class AbstractEventCallback<T> implements Callback<ResponseBody> {
    private static final ObjectMapper mapper = Utils.defaultObjectMapper();
    protected FlowableEmitter<T> emitter;

    public AbstractEventCallback(FlowableEmitter<T> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        BufferedReader reader = null;

        try {
            String logID = Utils.getLogID(response);
            if (!response.isSuccessful()) {
                String errStr = response.errorBody().string();
                CozeError error = mapper.readValue(errStr, CozeError.class);
                throw new CozeApiExcetion(Integer.valueOf(response.code()), error.getErrorMessage(), logID);
            }

            // 检查 response body 是否为 BaseResponse 格式
            String contentType = response.headers().get("Content-Type");
            if (contentType != null && contentType.contains("application/json")) {
                String respStr = response.body().string();
                BaseResponse<?> baseResp = mapper.readValue(respStr, BaseResponse.class);
                if (baseResp.getCode() != 0) {
                    throw new CozeApiExcetion(baseResp.getCode(), baseResp.getMsg(), logID);
                }
                return;
            }

            InputStream in = response.body().byteStream();
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;

            while (!emitter.isCancelled() && (line = reader.readLine()) != null) {
                if (processLine(line, reader, logID)) {
                    break;
                }
            }

            emitter.onComplete();

        } catch (Throwable t) {
            onFailure(call, t);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // do nothing
                }
                if (response.body() != null) {
                    response.body().close();
                }
            }
        }
    }

    protected abstract boolean processLine(String line, BufferedReader reader, String logID) throws IOException;

     @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        emitter.onError(t);
    }
} 