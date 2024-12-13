/* (C)2024 */
package com.coze.openapi.service.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.CozeAuthAPI;
import com.coze.openapi.client.auth.GetAccessTokenReq;
import com.coze.openapi.client.auth.GetPKCEAuthURLResp;
import com.coze.openapi.client.auth.GrantType;
import com.coze.openapi.client.auth.OAuthToken;

import io.reactivex.Single;
import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import retrofit2.Response;

class PKCEOAuthClientTest {

  @Mock private CozeAuthAPI mockApi;

  private PKCEOAuthClient client;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    client = new PKCEOAuthClient.PKCEOAuthBuilder().clientID("test_client_id").build();
    TestUtils.setField(client, "api", mockApi);
  }

  @Test
  void testGenOAuthURLWithPlainMethod() {
    // 执行测试
    GetPKCEAuthURLResp result = client.genOAuthURL("https://test.com/callback", "test_state");

    // 验证结果
    assertNotNull(result);
    assertNotNull(result.getCodeVerifier());
    assertEquals(16, result.getCodeVerifier().length());
    assertTrue(result.getAuthorizationURL().contains("response_type=code"));
    assertTrue(result.getAuthorizationURL().contains("client_id=test_client_id"));
    assertTrue(
        result.getAuthorizationURL().contains("redirect_uri=https%3A%2F%2Ftest.com%2Fcallback"));
    assertTrue(result.getAuthorizationURL().contains("state=test_state"));
    assertTrue(result.getAuthorizationURL().contains("code_challenge=" + result.getCodeVerifier()));
    assertTrue(result.getAuthorizationURL().contains("code_challenge_method=plain"));
  }

  @Test
  void testGenOAuthURLWithS256Method() {
    // 执行测试
    GetPKCEAuthURLResp result =
        client.genOAuthURL(
            "https://test.com/callback", "test_state", PKCEOAuthClient.CodeChallengeMethod.S256);

    // 验证结果
    assertNotNull(result);
    assertNotNull(result.getCodeVerifier());
    assertEquals(16, result.getCodeVerifier().length());
    assertTrue(result.getAuthorizationURL().contains("response_type=code"));
    assertTrue(result.getAuthorizationURL().contains("client_id=test_client_id"));
    assertTrue(
        result.getAuthorizationURL().contains("redirect_uri=https%3A%2F%2Ftest.com%2Fcallback"));
    assertTrue(result.getAuthorizationURL().contains("state=test_state"));
    assertTrue(result.getAuthorizationURL().contains("code_challenge="));
    assertTrue(result.getAuthorizationURL().contains("code_challenge_method=S256"));
  }

  @Test
  void testGenOAuthURLWithWorkspaceID() {
    // 执行测试
    GetPKCEAuthURLResp result =
        client.genOAuthURL("https://test.com/callback", "test_state", "test_workspace_id");

    // 验证结果
    assertNotNull(result);
    assertTrue(result.getAuthorizationURL().contains("/workspace_id/test_workspace_id/authorize"));
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
    OAuthToken result =
        client.getAccessToken("test_code", "https://test.com/callback", "test_code_verifier");

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
                  assertEquals("test_code_verifier", req.getCodeVerifier());
                  return true;
                }));
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
  }

  @Test
  void testGenS256CodeChallenge() throws NoSuchAlgorithmException {
    String codeVerifier = "test_code_verifier";
    String codeChallenge = PKCEOAuthClient.genS256CodeChallenge(codeVerifier);
    assertNotNull(codeChallenge);
    assertFalse(codeChallenge.contains("="));
  }
}
