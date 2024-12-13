/* (C)2024 */
package com.coze.openapi.service.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.CozeAuthAPI;
import com.coze.openapi.client.auth.DeviceAuthReq;
import com.coze.openapi.client.auth.DeviceAuthResp;
import com.coze.openapi.client.auth.GetAccessTokenReq;
import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.exception.CozeAuthException;

import io.reactivex.Single;
import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import retrofit2.Response;

class DeviceOAuthClientTest {

  @Mock private CozeAuthAPI mockApi;

  private DeviceOAuthClient client;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    client =
        new DeviceOAuthClient.DeviceOAuthBuilder()
            .clientID("test_client_id")
            .clientSecret("test_client_secret")
            .build();
    // 通过反射设置 mockApi
    TestUtils.setField(client, "api", mockApi);
  }

  @Test
  void testGetDeviceCode() {
    // 准备测试数据
    DeviceAuthResp mockResp = new DeviceAuthResp();
    mockResp.setDeviceCode("device_code");
    mockResp.setUserCode("user_code");
    mockResp.setVerificationURI("https://verify.test.com");
    mockResp.setExpiresIn(1800);
    mockResp.setInterval(5);

    Response<DeviceAuthResp> response =
        Response.success(
            mockResp,
            new okhttp3.Response.Builder()
                .request(new Request.Builder().url("https://test.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .headers(Headers.of("X-Log-Id", "test_log_id"))
                .build());

    // 设置模拟行为
    when(mockApi.device(any(DeviceAuthReq.class))).thenReturn(Single.just(response));

    // 执行测试
    DeviceAuthResp result = client.getDeviceCode();

    // 验证结果
    assertNotNull(result);
    assertEquals("device_code", result.getDeviceCode());
    assertEquals("user_code", result.getUserCode());
    assertEquals("https://verify.test.com?user_code=user_code", result.getVerificationURL());
  }

  @Test
  void testGetDeviceCodeWithWorkspaceID() {
    // 准备测试数据
    DeviceAuthResp mockResp = new DeviceAuthResp();
    mockResp.setDeviceCode("device_code");
    mockResp.setUserCode("user_code");
    mockResp.setVerificationURI("https://verify.test.com");
    mockResp.setExpiresIn(1800);
    mockResp.setInterval(5);

    Response<DeviceAuthResp> response =
        Response.success(
            mockResp,
            new okhttp3.Response.Builder()
                .request(new Request.Builder().url("https://test.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .headers(Headers.of("X-Log-Id", "test_log_id"))
                .build());

    // 设置模拟行为
    when(mockApi.device(anyString(), any(DeviceAuthReq.class))).thenReturn(Single.just(response));

    // 执行测试
    DeviceAuthResp result = client.getDeviceCode("test_workspace_id");

    // 验证结果
    assertNotNull(result);
    assertEquals("device_code", result.getDeviceCode());
    assertEquals("user_code", result.getUserCode());
    assertEquals("https://verify.test.com?user_code=user_code", result.getVerificationURL());
  }

  @Test
  void testGetAccessTokenWithPolling() throws Exception {
    // 准备测试数据
    OAuthToken mockToken = new OAuthToken();
    mockToken.setAccessToken("access_token");
    mockToken.setExpiresIn(3600);
    mockToken.setRefreshToken("refresh_token");

    Response<OAuthToken> successResponse =
        Response.success(
            mockToken,
            new okhttp3.Response.Builder()
                .request(new Request.Builder().url("https://test.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .headers(Headers.of("X-Log-Id", "test_log_id"))
                .build());

    Response<OAuthToken> pendingResponse =
        Response.error(
            400,
            okhttp3.ResponseBody.create(
                "{\"error_code\":\"authorization_pending\",\"error_message\":\"Authorization pending\"}",
                okhttp3.MediaType.get("application/json")));
    Response<OAuthToken> slowDownResponse =
            Response.error(
                    400,
                    okhttp3.ResponseBody.create(
                            "{\"error_code\":\"slow_down\",\"error_message\":\"Slow Down\"}",
                            okhttp3.MediaType.get("application/json")));

    // 设置模拟行为 - 第一次返回 pending，第二次返回 slow down，第三次成功
    when(mockApi.retrieve(any(), any()))
        .thenReturn(Single.just(pendingResponse))
        .thenReturn(Single.just(slowDownResponse))
        .thenReturn(Single.just(successResponse));

    // 执行测试
    OAuthToken result = client.getAccessToken("device_code", true);

    // 验证结果
    assertNotNull(result);
    assertEquals("access_token", result.getAccessToken());
    assertEquals("refresh_token", result.getRefreshToken());
    assertEquals(Integer.valueOf(3600), result.getExpiresIn());
  }

  @Test
  void testGetAccessTokenWithError() {
    // 准备错误响应
    Response<OAuthToken> errorResponse =
        Response.error(
            400,
            okhttp3.ResponseBody.create(
                "{\"error_code\":\"invalid_grant\",\"error_message\":\"Invalid grant\"}",
                okhttp3.MediaType.get("application/json")));

    // 设置模拟行为
    when(mockApi.retrieve(anyMap(), any(GetAccessTokenReq.class)))
        .thenReturn(Single.just(errorResponse));

    // 执行测试并验证异常
    CozeAuthException exception =
        assertThrows(
            CozeAuthException.class,
            () -> {
              client.getAccessToken("device_code", false);
            });

    assertEquals("invalid_grant", exception.getCode().getValue());
    assertTrue(exception.getMessage().contains("Invalid grant"));
  }

  @Test
  void testRefreshToken() {
    // 准备测试数据
    OAuthToken mockToken = new OAuthToken();
    mockToken.setAccessToken("new_access_token");
    mockToken.setExpiresIn(3600);
    mockToken.setRefreshToken("new_refresh_token");

    Response<OAuthToken> response =
        Response.success(
            mockToken,
            new okhttp3.Response.Builder()
                .request(new Request.Builder().url("https://test.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .headers(Headers.of("X-Log-Id", "test_log_id"))
                .build());

    // 设置模拟行为
    when(mockApi.retrieve(anyMap(), any(GetAccessTokenReq.class)))
        .thenReturn(Single.just(response));

    // 执行测试
    OAuthToken result = client.refreshToken("refresh_token");

    // 验证结果
    assertNotNull(result);
    assertEquals("new_access_token", result.getAccessToken());
    assertEquals("new_refresh_token", result.getRefreshToken());
    assertEquals(Integer.valueOf(3600), result.getExpiresIn());
  }
}
