package com.coze.openapi.service.utils;


import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.common.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.Single;
import retrofit2.HttpException;
import retrofit2.Response;

import java.security.SecureRandom;

public class Utils {
    public static final String LOG_HEADER = "x-tt-logid";
    private static final ObjectMapper mapper = defaultObjectMapper();
    public static <T> T execute(Single<Response<T>> apiCall) {
        try {
            Response<T> response = apiCall.blockingGet();
            if (!response.isSuccessful()){
                throw new HttpException(response);
            }
            T body = response.body();
            if (body instanceof BaseResponse) {
                ((BaseResponse<?>) body).setLogID(getLogID(response));
                if (((BaseResponse<?>)body).getData() instanceof BaseResp) {
                    ((BaseResp) ((BaseResponse<?>)body).getData()).setLogID(getLogID(response));
                }
            } else if (body instanceof BaseResp) {
                ((BaseResp) body).setLogID(getLogID(response));
            }
            return body;
        } catch (HttpException e) {
            throw e;
        }
    }

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    public static String getLogID(Response<?> response) {
        return response.raw().headers().get(LOG_HEADER);
    }

    public static String toJson(Object obj) {
        try{
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON string", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse object from JSON string", e);
        }
    }

    public static String genRandomSign(int length) {
        byte[] bytes = new byte[length / 2];
        new SecureRandom().nextBytes(bytes);
        return bytesToHex(bytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
