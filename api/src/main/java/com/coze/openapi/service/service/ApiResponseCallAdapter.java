package com.coze.openapi.service.service;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.exception.CozeApiExcetion;
import com.coze.openapi.service.utils.Utils;

import io.jsonwebtoken.io.IOException;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.CallAdapter;

import java.lang.reflect.Type;

public class ApiResponseCallAdapter<R> implements CallAdapter<R, Single<?>> {
    private static final String logHeader = "x-tt-logid";
    private final Type responseType;

    public ApiResponseCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Single<?> adapt(Call<R> call) {
        return Single.fromCallable(() -> {
            // todo 添加日志
            retrofit2.Response<R> response = call.execute();
            if (!response.isSuccessful()) {
                try{
                    BaseResponse<?> baseResponse = Utils.fromJson(response.errorBody().string(), BaseResponse.class);
                    throw new CozeApiExcetion(baseResponse.getCode(), baseResponse.getMsg(), Utils.getLogID(response));
                } catch (IOException e) {
                    throw new RuntimeException("HTTP error: " + response.code() + " " + e.getMessage());
                }
            }
            try{
                BaseResponse<?> baseResponse = (BaseResponse<?>) response.body();
                if (baseResponse.getCode() != 0) {
                    throw new CozeApiExcetion(baseResponse.getCode(), baseResponse.getMsg(), Utils.getLogID(response));
                }
                return response.body();
            } catch (IOException e) {
                throw new RuntimeException("HTTP error: " + response.code() + " " + e.getMessage());
            } catch (ClassCastException ex) {
                // 如果返回的不是 BaseResponse，则直接返回
                return response.body();
            }
        });
    }
}
 