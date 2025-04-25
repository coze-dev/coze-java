package com.coze.openapi.service.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coze.openapi.client.auth.GetJWTAccessTokenReq;
import com.coze.openapi.client.auth.OAuthToken;

@ExtendWith(MockitoExtension.class)
class JWTOAuthTest {

  @Mock private JWTOAuthClient jwtClient;

  @Mock private OAuthToken oAuthToken;

  private JWTOAuth jwtOAuth;

  @Test
  void testConstructorWithNullClient() {
    assertThrows(NullPointerException.class, () -> new JWTOAuth(null));
  }

  @Test
  void testConstructorWithValidClient() {
    when(jwtClient.getTtl()).thenReturn(600);
    jwtOAuth = new JWTOAuth(jwtClient);
    assertNotNull(jwtOAuth);
  }

  @Test
  void testTokenFirstTime() {
    when(jwtClient.getTtl()).thenReturn(600);
    when(oAuthToken.getAccessToken()).thenReturn("test-token");
    when(jwtClient.getAccessToken((GetJWTAccessTokenReq) any())).thenReturn(oAuthToken);

    jwtOAuth = new JWTOAuth(jwtClient);
    String token = jwtOAuth.token();

    assertEquals("test-token", token);
    verify(jwtClient, times(1)).getAccessToken((GetJWTAccessTokenReq) any());
  }

  @Test
  void testTokenCaching() {
    when(jwtClient.getTtl()).thenReturn(600);
    when(oAuthToken.getAccessToken()).thenReturn("test-token");
    when(oAuthToken.getExpiresIn()).thenReturn(600);
    when(jwtClient.getAccessToken((GetJWTAccessTokenReq) any())).thenReturn(oAuthToken);

    jwtOAuth = new JWTOAuth(jwtClient);

    String token1 = jwtOAuth.token();
    String token2 = jwtOAuth.token();

    assertEquals(token1, token2);
    verify(jwtClient, times(2)).getAccessToken((GetJWTAccessTokenReq) any());
  }

  @Test
  void testTokenWithCustomParameters() {
    Integer ttl = 300;
    String sessionName = "test-session";

    when(oAuthToken.getAccessToken()).thenReturn("test-token");
    when(jwtClient.getAccessToken((GetJWTAccessTokenReq) any())).thenReturn(oAuthToken);

    jwtOAuth = JWTOAuth.builder().jwtClient(jwtClient).ttl(ttl).sessionName(sessionName).build();

    String token = jwtOAuth.token();

    assertEquals("test-token", token);
    verify(jwtClient).getAccessToken((GetJWTAccessTokenReq) any());
  }

  @Test
  void testTokenRefresh() throws InterruptedException {
    when(oAuthToken.getAccessToken()).thenReturn("test-token");
    when(oAuthToken.getExpiresIn()).thenReturn(2);
    when(jwtClient.getAccessToken((GetJWTAccessTokenReq) any())).thenReturn(oAuthToken);

    jwtOAuth = JWTOAuth.builder().jwtClient(jwtClient).ttl(2).build();

    String token1 = jwtOAuth.token();
    Thread.sleep(3000);
    String token2 = jwtOAuth.token();

    assertEquals("test-token", token2);
    verify(jwtClient, times(2)).getAccessToken((GetJWTAccessTokenReq) any());
  }
}
