/* (C)2024 */
package com.coze.openapi.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.Type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.exception.CozeApiExcetion;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class APIResponseCallAdapterTest {

  @Mock private Call<BaseResponse<String>> mockCall;

  private APIResponseCallAdapter<BaseResponse<String>> adapter;
  private Type responseType;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    responseType = BaseResponse.class;
    adapter = new APIResponseCallAdapter<>(responseType);
  }

  @Test
  public void testResponseType() {
    assertEquals(responseType, adapter.responseType());
  }

  @Test
  public void testSuccessfulResponse() throws IOException {
    // 准备测试数据
    BaseResponse<String> successResponse = new BaseResponse<>();
    successResponse.setCode(0);
    successResponse.setMsg("success");
    successResponse.setData("test data");

    Request request = new Request.Builder().url("https://api.test.com").build();

    Response<BaseResponse<String>> response = Response.success(successResponse);

    // 设置模拟行为
    when(mockCall.execute()).thenReturn(response);
    when(mockCall.request()).thenReturn(request);

    // 执行测试
    Call<BaseResponse<String>> adaptedCall = adapter.adapt(mockCall);
    Response<BaseResponse<String>> result = adaptedCall.execute();

    // 验证结果
    assertTrue(result.isSuccessful());
    assertEquals(0L, (long) result.body().getCode());
    assertEquals("success", result.body().getMsg());
    assertEquals("test data", result.body().getData());
  }

  @Test
  void testErrorResponse() {
    assertThrows(
        CozeApiExcetion.class,
        () -> {
          // 准备错误响应
          String errorJson = "{\"code\":400,\"msg\":\"Bad Request\"}";
          ResponseBody errorBody =
              ResponseBody.create(errorJson, MediaType.parse("application/json"));

          Request request = new Request.Builder().url("https://api.test.com").build();

          Response<BaseResponse<String>> errorResponse = Response.error(400, errorBody);

          // 设置模拟行为
          when(mockCall.execute()).thenReturn(errorResponse);
          when(mockCall.request()).thenReturn(request);

          // 执行测试
          Call<BaseResponse<String>> adaptedCall = adapter.adapt(mockCall);
          adaptedCall.execute();
        });
  }

  @Test
  void testBusinessErrorResponse() {
    assertThrows(
        CozeApiExcetion.class,
        () -> {
          // 准备业务错误响应
          BaseResponse<String> errorResponse = new BaseResponse<>();
          errorResponse.setCode(1001);
          errorResponse.setMsg("Business Error");

          Request request = new Request.Builder().url("https://api.test.com").build();

          Response<BaseResponse<String>> response = Response.success(errorResponse);

          // 设置模拟行为
          when(mockCall.execute()).thenReturn(response);
          when(mockCall.request()).thenReturn(request);

          // 执行测试
          Call<BaseResponse<String>> adaptedCall = adapter.adapt(mockCall);
          adaptedCall.execute();
        });
  }

  @Test
  public void testAsyncCall() {
    // 测试异步调用相关方法
    Call<BaseResponse<String>> adaptedCall = adapter.adapt(mockCall);

    assertFalse(adaptedCall.isExecuted());
    assertFalse(adaptedCall.isCanceled());

    adaptedCall.cancel();
    verify(mockCall).cancel();

    Request originalRequest = new Request.Builder().url("https://api.test.com").build();
    when(mockCall.request()).thenReturn(originalRequest);

    assertEquals(originalRequest, adaptedCall.request());
  }

  @Test
  public void testCloneCall() {
    // 准备克隆调用测试
    when(mockCall.clone()).thenReturn(mockCall);

    Call<BaseResponse<String>> adaptedCall = adapter.adapt(mockCall);
    Call<BaseResponse<String>> clonedCall = adaptedCall.clone();

    assertNotNull(clonedCall);
    assertNotSame(adaptedCall, clonedCall);
  }
}
