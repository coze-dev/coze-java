/* (C)2024 */
package com.coze.openapi.service.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentInterceptorTest {

  private UserAgentInterceptor interceptor;

  @Mock private Interceptor.Chain chain;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    interceptor = new UserAgentInterceptor();
  }

  @Test
  public void testIntercept() throws IOException {
    // 准备测试数据
    Request originalRequest = new Request.Builder().url("https://api.example.com").build();

    Response mockResponse =
        new Response.Builder()
            .request(originalRequest)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build();

    // 设置模拟行为
    when(chain.request()).thenReturn(originalRequest);
    when(chain.proceed(any(Request.class))).thenReturn(mockResponse);

    // 执行测试
    Response response = interceptor.intercept(chain);

    // 验证结果
    verify(chain)
        .proceed(
            argThat(
                new ArgumentMatcher<Request>() {
                  @Override
                  public boolean matches(Request request) {
                    String cozeClientUserAgent = request.header("X-Coze-Client-User-Agent");

                    assertNotNull(request.header("User-Agent"));

                    // 验证 X-Coze-Client-User-Agent 是有效的 JSON
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                      JsonNode json = mapper.readTree(cozeClientUserAgent);
                      assertTrue(json.has("version"));
                      assertTrue(json.has("lang"));
                      assertTrue(json.has("lang_version"));
                      assertTrue(json.has("os_name"));
                      assertTrue(json.has("os_version"));
                    } catch (Exception e) {
                      fail("Invalid JSON in X-Coze-Client-User-Agent header");
                    }

                    return true;
                  }
                }));

    assertNotNull(response);
    assertEquals(200, response.code());
  }

  @Test
  public void testUserAgentFormat() throws IOException {
    System.setProperty("java.version", "11.0.12");
    System.setProperty("os.name", "Mac OS X");
    System.setProperty("os.version", "10.15.7");

    Request originalRequest = new Request.Builder().url("https://api.example.com").build();

    when(chain.request()).thenReturn(originalRequest);
    when(chain.proceed(any(Request.class)))
        .thenAnswer(
            invocation -> {
              Request request = (Request) invocation.getArguments()[0];
              String userAgent = request.header("User-Agent");

              assertNotNull(userAgent);

              return new Response.Builder()
                  .request(request)
                  .protocol(Protocol.HTTP_1_1)
                  .code(200)
                  .message("OK")
                  .build();
            });

    interceptor.intercept(chain);
  }

  @Test
  public void testUserAgentFormatWindows() throws IOException {
    // 设置 Windows 系统环境变量
    System.setProperty("java.version", "17.0.2");
    System.setProperty("os.name", "Windows 10");
    System.setProperty("os.version", "10.0");

    Request originalRequest = new Request.Builder().url("https://api.example.com").build();

    when(chain.request()).thenReturn(originalRequest);
    when(chain.proceed(any(Request.class)))
        .thenAnswer(
            invocation -> {
              Request request = (Request) invocation.getArguments()[0];

              assertNotNull(request.header("User-Agent"));

              return new Response.Builder()
                  .request(request)
                  .protocol(Protocol.HTTP_1_1)
                  .code(200)
                  .message("OK")
                  .build();
            });

    interceptor.intercept(chain);
  }
}
