/* (C)2024 */
package com.coze.openapi.service.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.CozeAuthAPI;
import com.coze.openapi.client.auth.GetAccessTokenReq;
import com.coze.openapi.client.auth.GrantType;
import com.coze.openapi.client.auth.OAuthToken;

import io.reactivex.Single;
import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import retrofit2.Response;

class WebOAuthClientTest {

  @Mock private CozeAuthAPI mockApi;

  private WebOAuthClient client;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    client =
        new WebOAuthClient.WebOAuthBuilder()
            .clientID("test_client_id")
            .clientSecret("test_client_secret")
            .build();
    TestUtils.setField(client, "api", mockApi);
  }

  @Test
  void testGetOAuthURL() {
    String url = client.getOAuthURL("https://test.com/callback", "test_state");

    assertTrue(url.contains("response_type=code"));
    assertTrue(url.contains("client_id=test_client_id"));
    assertTrue(url.contains("redirect_uri=https%3A%2F%2Ftest.com%2Fcallback"));
    assertTrue(url.contains("state=test_state"));
  }

  @Test
  void testGetOAuthURLWithWorkspaceID() {
    String url = client.getOAuthURL("https://test.com/callback", "test_state", "test_workspace_id");

    assertTrue(url.contains("/workspace_id/test_workspace_id/authorize"));
    assertTrue(url.contains("response_type=code"));
    assertTrue(url.contains("client_id=test_client_id"));
    assertTrue(url.contains("redirect_uri=https%3A%2F%2Ftest.com%2Fcallback"));
    assertTrue(url.contains("state=test_state"));
  }

  @Test
  void testGetAccessToken() {
    // 准备测试数据
    OAuthToken mockToken = new OAuthToken();
    mockToken.setAccessToken("access_token");
    mockToken.setExpiresIn(3600);
    mockToken.setRefreshToken("refresh_token");

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
    OAuthToken result = client.getAccessToken("test_code", "https://test.com/callback");

    // 验证结果
    assertNotNull(result);
    assertEquals("access_token", result.getAccessToken());
    assertEquals("refresh_token", result.getRefreshToken());
    assertEquals(Integer.valueOf(3600), result.getExpiresIn());

    // 验证请求参数
    verify(mockApi)
        .retrieve(
            anyMap(),
            argThat(
                req -> {
                  assertEquals(GrantType.AuthorizationCode.getValue(), req.getGrantType());
                  assertEquals("test_code", req.getCode());
                  assertEquals("https://test.com/callback", req.getRedirectUri());
                  return true;
                }));

    // 验证 Authorization header
    verify(mockApi)
        .retrieve(
            argThat(headers -> headers.get("Authorization").equals("Bearer test_client_secret")),
            any(GetAccessTokenReq.class));
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

    // 验证请求参数
    verify(mockApi)
        .retrieve(
            anyMap(),
            argThat(
                req -> {
                  assertEquals(GrantType.RefreshToken.getValue(), req.getGrantType());
                  assertEquals("refresh_token", req.getRefreshToken());
                  return true;
                }));

    // 验证 Authorization header
    verify(mockApi)
        .retrieve(
            argThat(headers -> headers.get("Authorization").equals("Bearer test_client_secret")),
            any(GetAccessTokenReq.class));
  }
}
