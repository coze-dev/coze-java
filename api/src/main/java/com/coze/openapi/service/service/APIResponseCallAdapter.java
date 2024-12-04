package com.coze.openapi.service.service;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.exception.CozeApiExcetion;
import com.coze.openapi.service.service.common.CozeLoggerFactory;
import com.coze.openapi.service.utils.Utils;

import io.jsonwebtoken.io.IOException;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.CallAdapter;

import java.lang.reflect.Type;

import org.slf4j.Logger;

public class APIResponseCallAdapter<R> implements CallAdapter<R, Single<?>> {
    private final Type responseType;
    private static final Logger logger = CozeLoggerFactory.getLogger();

    public APIResponseCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Single<?> adapt(Call<R> call) {
        return Single.fromCallable(() -> {
            retrofit2.Response<R> response = call.execute();
            if (!response.isSuccessful()) {
                logger.warn("HTTP error: " + response.code() + " " + response.message());
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
                    logger.warn("API error: " + baseResponse.getCode() + " " + baseResponse.getMsg());
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
 