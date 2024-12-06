package com.coze.openapi.service.service;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.exception.CozeApiExcetion;
import com.coze.openapi.service.utils.Utils;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.*;

import java.io.IOException;
import java.lang.reflect.Type;

public class APIResponseCallAdapter<R> implements CallAdapter<R, Call<R>> {
    private static final Logger logger = LoggerFactory.getLogger(APIResponseCallAdapter.class);
    private final Type responseType;

    public APIResponseCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Call<R> adapt(Call<R> call) {
        return new Call<R>() {
            @Override
            public Response<R> execute() throws IOException {
                Response<R> response = call.execute();
                if (!response.isSuccessful()) {
                    logger.warn("HTTP error: " + response.code() + " " + response.message());
                    String errStr = "http exception";
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody != null) {
                            errStr = errorBody.string();
                            BaseResponse<?> baseResponse = Utils.fromJson(errStr, BaseResponse.class);
                            throw new CozeApiExcetion(baseResponse.getCode(), baseResponse.getMsg(), Utils.getLogID(response));
                        }
                        throw new CozeApiExcetion(response.code(), "http exception", Utils.getLogID(response));
                    } catch (Exception e) {
                        // 解析json 失败会走到这，直接返回全量信息
                        throw new CozeApiExcetion(response.code(), errStr, Utils.getLogID(response));
                    }
                }

                R body = response.body();
                if (body instanceof BaseResponse) {
                    BaseResponse<?> baseResponse = (BaseResponse<?>) body;
                    if (baseResponse.getCode() != 0) {
                        logger.warn("API error: " + baseResponse.getCode() + " " + baseResponse.getMsg());
                        throw new CozeApiExcetion(baseResponse.getCode(), baseResponse.getMsg(), Utils.getLogID(response));
                    }
                }
                return response;
            }

            @Override
            public void enqueue(Callback<R> callback) {
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {
                        try {
                            callback.onResponse(call, execute());
                        } catch (Exception e) {
                            onFailure(call, e);
                        }
                    }

                    @Override
                    public void onFailure(Call<R> call, Throwable t) {
                        callback.onFailure(call, t);
                    }
                });
            }

            @Override
            public boolean isExecuted() {
                return call.isExecuted();
            }

            @Override
            public void cancel() {
                call.cancel();
            }

            @Override
            public boolean isCanceled() {
                return call.isCanceled();
            }

            @Override
            public Call<R> clone() {
                return adapt(call.clone());
            }

            @Override
            public Request request() {
                return call.request();
            }

            @Override
            public okio.Timeout timeout() {
                return call.timeout();
            }
        };
    }
}