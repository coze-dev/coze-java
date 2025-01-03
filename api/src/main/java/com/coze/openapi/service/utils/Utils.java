package com.coze.openapi.service.utils;

import java.security.SecureRandom;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.common.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class Utils {
  public static final String LOG_HEADER = "x-tt-logid";
  private static final ObjectMapper mapper = defaultObjectMapper();

  public static <T> T execute(Call<T> call) {
    try {
      Response<T> response = call.execute();
      if (!response.isSuccessful()) {
        throw new HttpException(response);
      }
      T body = response.body();

      // 处理不同类型的响应
      if (body instanceof BaseResponse) {
        BaseResponse<?> baseResponse = (BaseResponse<?>) body;
        baseResponse.setLogID(getLogID(response));

        if (baseResponse.getData() instanceof BaseResp) {
          BaseResp baseResp = (BaseResp) baseResponse.getData();
          baseResp.setLogID(getLogID(response));
        }
      } else if (body instanceof BaseResp) {
        BaseResp baseResp = (BaseResp) body;
        baseResp.setLogID(getLogID(response));
      }

      return body;
    } catch (Exception e) {
      throw new RuntimeException(e);
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
    try {
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
