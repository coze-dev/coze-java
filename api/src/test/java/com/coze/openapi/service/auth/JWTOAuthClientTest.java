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
import com.coze.openapi.client.auth.scope.Scope;

import io.reactivex.Single;
import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import retrofit2.Response;

class JWTOAuthClientTest {

  @Mock private CozeAuthAPI mockApi;

  private JWTOAuthClient client;

  private static final String TEST_PRIVATE_KEY =
      "-----BEGIN PRIVATE KEY-----\n"
          + "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCj1Mlf7zfg/kx4\n"
          + "DHogPkN7gTkAYi7FM6TktFZFHDm8Zs1KiL6WrpU+UTqBiHHhlMVB3RiaJxWH40ia\n"
          + "9OWJvIpM5lCaMnzGNX/4wC+4Pxc3KNoUhijP6ofS4yI5xSpUyMrjl9q95ePBNmmP\n"
          + "Tv+s4uTa2y0e1ZlDHwIWC8InZ5NX65RO+yIF+95gclFkANgp5l7aBHaLiSebYRJT\n"
          + "aluZmS4ZUH06Y9LHkS+QvuvOPaQu3Y+xdgHnzEYtNn83tTmLCBAt2ZYcJi0WIeJZ\n"
          + "acaLsi59N1LH+2ZFtMc6+l7qlB0i4m7Dko+9i9OGtBD4y6rMO85VKUAQTs862O3W\n"
          + "KIsWsKXjAgMBAAECggEAAoxg5uxK9O1WTFg3OOw7QEDoUjHLXWPKQtP8sxNxrFjo\n"
          + "yFcx1WQTdYRXHioasuikNn/Tc6vOyc/bXdnq/jzlXg/pbByaWEH/XwHhHgbNNJXb\n"
          + "JhXfrVlv+zAkGXE9czVYILF1xIcgcKI9zhsYl0IXT1gxMmwO98XX0lisPcHY7IhV\n"
          + "JqSGg9cpLi7agyu4E6xBnK8B7rlk34WOrQf7WElwZ+1bddqA2WLmlls5m3dcJ6IF\n"
          + "kJAEMmHYlkpNBC5fhocui0enfVxDncVghZFMugmY6AtxY8kB2U5Fy1hFHi0Eu9Yg\n"
          + "I9XDJD4S/vzpoKojeAVFr/iQkzTj/ObzeF6gaFWN0QKBgQDlM9l69oQX/p94jr9t\n"
          + "6U2G3BK2NJk/O2j1jcOYX7ud1erdRlfeGJwEpReYQ6Ug+cLc3n3cj8qWg2x2Yw8L\n"
          + "45bVuJPxfJ0KPWI03syb+llAsIY3MC70quNu4b9vDTNS6pN6F4trTvT0Woz0x4vo\n"
          + "i3pz3u3NPnfL1I0EoPKobDf7bwKBgQC2/FbOpXTM7a1UHVgd2y1OKzpGcuC0eOKN\n"
          + "/DO2P24CFCgAdySnzsfLYlIKoU8DYvEndyIVysZav6pNC5PJc0vpJ6Oqg3izXigw\n"
          + "viM5CJhFVxPWrtyMcN02JNUSHNWOaiuCOlZIPQEgYCTUECjE/Xl1COonVS38mO+N\n"
          + "FSF7Z3mSzQKBgEmX+2W7D7Dwpd284AR3m9gIg82TV/1wowPtT/d2DbThQfdopb//\n"
          + "YOEw7UGLvtK2v3XRztHqLZ9kdYgRyHwFyKG5EW/Bll76VLUrMMGIge3+gCnqQ7l1\n"
          + "wW8R9zi+IVOnVFEojDCZepeXF5llFSxG1Lutwedb/nUpO1pYH3IqxVLrAoGBAIVv\n"
          + "MSXzhV7CmrhRxaXP5BOydgZVUwKHfD2pgVQOoPunExxzxSkRIqRvCAB0bJe9mLj8\n"
          + "qMBXY5ldVqRkItqt1tcobrKyuFuj947DuA+o8tDtlKviSzWmP8lxxmY03I3DYgLO\n"
          + "44g95Apl0bVKK1CqvdzYKVeRR72BEH5CwG2qoP6pAoGAUpvD0LSVh171UwQFkT6K\n"
          + "b2mWBz1LV7EYLg4bfmi7wRBUCeEuK16+PEJ63yYUg8cSGTZqOFyRbc4tNf2Ow8BL\n"
          + "gpsiuY9Mn2TnbscpeK841s68IHx4l90Je4tbbjK4E/yv+vgARkiiWQbG0BZSkBjO\n"
          + "qI39/arl6ZhTeQMv7TrpQ6Q=\n";

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    client =
        new JWTOAuthClient.JWTOAuthBuilder()
            .clientID("test_client_id")
            .publicKey("test_public_key")
            .privateKey(TEST_PRIVATE_KEY)
            .ttl(900)
            .build();
    TestUtils.setField(client, "api", mockApi);
  }

  @Test
  void testGetAccessToken() {
    // 准备测试数据
    OAuthToken mockToken = new OAuthToken();
    mockToken.setAccessToken("access_token");
    mockToken.setExpiresIn(900);

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
    OAuthToken result = client.getAccessToken();

    // 验证结果
    assertNotNull(result);
    assertEquals("access_token", result.getAccessToken());
    assertEquals(Integer.valueOf(900), result.getExpiresIn());

    // 验证请求参数
    verify(mockApi)
        .retrieve(
            anyMap(),
            argThat(
                req -> {
                  assertEquals(GrantType.JWT_CODE.getValue(), req.getGrantType());
                  assertEquals(900, req.getDurationSeconds());
                  return true;
                }));
  }

  @Test
  void testGetAccessTokenWithCustomTTL() {
    // 准备测试数据
    OAuthToken mockToken = new OAuthToken();
    mockToken.setAccessToken("access_token");
    mockToken.setExpiresIn(1800);

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
    OAuthToken result = client.getAccessToken(1800);

    // 验证结果
    assertNotNull(result);
    assertEquals("access_token", result.getAccessToken());
    assertEquals(Integer.valueOf(1800), result.getExpiresIn());

    // 验证请求参数
    verify(mockApi)
        .retrieve(
            anyMap(),
            argThat(
                req -> {
                  assertEquals(GrantType.JWT_CODE.getValue(), req.getGrantType());
                  assertEquals(1800, req.getDurationSeconds());
                  return true;
                }));
  }

  @Test
  void testGetAccessTokenWithScope() {
    // 准备测试数据
    OAuthToken mockToken = new OAuthToken();
    mockToken.setAccessToken("access_token");
    mockToken.setExpiresIn(900);

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
    Scope testScope = mock(Scope.class);
    when(testScope.toString()).thenReturn("test.scope");
    OAuthToken result = client.getAccessToken(testScope);

    // 验证结果
    assertNotNull(result);
    assertEquals("access_token", result.getAccessToken());
    assertEquals(Integer.valueOf(900), result.getExpiresIn());

    // 验证请求参数
    verify(mockApi)
        .retrieve(
            anyMap(),
            argThat(
                req -> {
                  assertEquals(GrantType.JWT_CODE.getValue(), req.getGrantType());
                  assertEquals("test.scope", req.getScope().toString());
                  return true;
                }));
  }

  @Test
  void testGetAccessTokenWithSessionName() {
    // 准备测试数据
    OAuthToken mockToken = new OAuthToken();
    mockToken.setAccessToken("access_token");
    mockToken.setExpiresIn(900);

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
    OAuthToken result = client.getAccessToken("test_session");

    // 验证结果
    assertNotNull(result);
    assertEquals("access_token", result.getAccessToken());
    assertEquals(Integer.valueOf(900), result.getExpiresIn());
  }

  @Test
  void testRefreshToken() {
    // JWT 认证不支持刷新令牌
    assertNull(client.refreshToken("refresh_token"));
  }
}
