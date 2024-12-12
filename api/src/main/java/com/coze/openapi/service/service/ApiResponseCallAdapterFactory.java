package com.coze.openapi.service.service;

import io.reactivex.Single;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.http.Streaming;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ApiResponseCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Single.class) {
            return null;
        }
        for (Annotation annotation : annotations) {
            // 流式接口不用这个 adapter 处理
            if (annotation instanceof Streaming ) {
                return null;
            }
        }
        Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
        return new ApiResponseCallAdapter<>(responseType);
    }

    public static ApiResponseCallAdapterFactory create() {
        return new ApiResponseCallAdapterFactory();
    }
}
