package com.coze.openapi.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.client.common.BaseReq;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

class TimeoutInterceptorTest {

  private TimeoutInterceptor interceptor;

  @Mock private Interceptor.Chain chain;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    interceptor = new TimeoutInterceptor();
  }

  @Test
  void testInterceptWithoutBaseReq() throws IOException {
    // 准备测试数据
    Request request = new Request.Builder().url("https://api.test.com").build();

    Response mockResponse =
        new Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build();

    // 设置模拟行为
    when(chain.request()).thenReturn(request);
    when(chain.proceed(request)).thenReturn(mockResponse);

    // 执行测试
    Response response = interceptor.intercept(chain);

    // 验证结果
    assertNotNull(response);
    verify(chain, never()).withConnectTimeout(anyInt(), any());
    verify(chain, never()).withReadTimeout(anyInt(), any());
    verify(chain, never()).withWriteTimeout(anyInt(), any());
  }

  @Test
  void testInterceptWithAllTimeouts() throws IOException {
    // 准备测试数据
    BaseReq baseReq =
        BaseReq.builder().connectTimeout(1000).readTimeout(2000).writeTimeout(3000).build();

    Request request =
        new Request.Builder().url("https://api.test.com").tag(BaseReq.class, baseReq).build();

    Response mockResponse =
        new Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build();

    // 设置模拟行为
    when(chain.request()).thenReturn(request);
    when(chain.withConnectTimeout(1000, TimeUnit.MILLISECONDS)).thenReturn(chain);
    when(chain.withReadTimeout(2000, TimeUnit.MILLISECONDS)).thenReturn(chain);
    when(chain.withWriteTimeout(3000, TimeUnit.MILLISECONDS)).thenReturn(chain);
    when(chain.proceed(request)).thenReturn(mockResponse);

    // 执行测试
    Response response = interceptor.intercept(chain);

    // 验证结果
    assertNotNull(response);
    verify(chain).withConnectTimeout(1000, TimeUnit.MILLISECONDS);
    verify(chain).withReadTimeout(2000, TimeUnit.MILLISECONDS);
    verify(chain).withWriteTimeout(3000, TimeUnit.MILLISECONDS);
  }

  @Test
  void testInterceptWithPartialTimeouts() throws IOException {
    // 准备测试数据 - 只设置连接超时
    BaseReq baseReq = BaseReq.builder().connectTimeout(1000).build();

    Request request =
        new Request.Builder().url("https://api.test.com").tag(BaseReq.class, baseReq).build();

    Response mockResponse =
        new Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build();

    // 设置模拟行为
    when(chain.request()).thenReturn(request);
    when(chain.withConnectTimeout(1000, TimeUnit.MILLISECONDS)).thenReturn(chain);
    when(chain.proceed(request)).thenReturn(mockResponse);

    // 执行测试
    Response response = interceptor.intercept(chain);

    // 验证结果
    assertNotNull(response);
    verify(chain).withConnectTimeout(1000, TimeUnit.MILLISECONDS);
    verify(chain, never()).withReadTimeout(anyInt(), any());
    verify(chain, never()).withWriteTimeout(anyInt(), any());
  }

  @Test
  void testInterceptWithInvalidTag() throws IOException {
    // 准备测试数据 - 使用非 BaseReq 类型的 tag
    Request request =
        new Request.Builder().url("https://api.test.com").tag(String.class, "invalid tag").build();

    Response mockResponse =
        new Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build();

    // 设置模拟行为
    when(chain.request()).thenReturn(request);
    when(chain.proceed(request)).thenReturn(mockResponse);

    // 执行测试
    Response response = interceptor.intercept(chain);

    // 验证结果
    assertNotNull(response);
    verify(chain, never()).withConnectTimeout(anyInt(), any());
    verify(chain, never()).withReadTimeout(anyInt(), any());
    verify(chain, never()).withWriteTimeout(anyInt(), any());
  }
}
