package com.coze.openapi.service.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    when(jwtClient.getAccessToken((Integer) any(), any(), any())).thenReturn(oAuthToken);

    jwtOAuth = new JWTOAuth(jwtClient);
    String token = jwtOAuth.token();

    assertEquals("test-token", token);
    verify(jwtClient, times(1)).getAccessToken((Integer) any(), any(), any());
  }

  @Test
  void testTokenCaching() {
    when(jwtClient.getTtl()).thenReturn(600);
    when(oAuthToken.getAccessToken()).thenReturn("test-token");
    when(oAuthToken.getExpiresIn()).thenReturn(600);
    when(jwtClient.getAccessToken((Integer) any(), any(), any())).thenReturn(oAuthToken);

    jwtOAuth = new JWTOAuth(jwtClient);

    String token1 = jwtOAuth.token();
    String token2 = jwtOAuth.token();

    assertEquals(token1, token2);
    verify(jwtClient, times(2)).getAccessToken((Integer) any(), any(), any());
  }

  @Test
  void testTokenWithCustomParameters() {
    Integer ttl = 300;
    String sessionName = "test-session";

    when(oAuthToken.getAccessToken()).thenReturn("test-token");
    when(jwtClient.getAccessToken(eq(ttl), any(), eq(sessionName))).thenReturn(oAuthToken);

    jwtOAuth = JWTOAuth.builder().jwtClient(jwtClient).ttl(ttl).sessionName(sessionName).build();

    String token = jwtOAuth.token();

    assertEquals("test-token", token);
    verify(jwtClient).getAccessToken(ttl, null, sessionName);
  }

  @Test
  void testTokenRefresh() throws InterruptedException {
    when(oAuthToken.getAccessToken()).thenReturn("test-token");
    when(oAuthToken.getExpiresIn()).thenReturn(2);
    when(jwtClient.getAccessToken((Integer) any(), any(), any())).thenReturn(oAuthToken);

    jwtOAuth = JWTOAuth.builder().jwtClient(jwtClient).ttl(2).build();

    String token1 = jwtOAuth.token();
    Thread.sleep(3000);
    String token2 = jwtOAuth.token();

    assertEquals("test-token", token2);
    verify(jwtClient, times(2)).getAccessToken((Integer) any(), any(), any());
  }

  @Test
  void testGetRefreshBeforeWithDifferentTtls() {
    when(oAuthToken.getAccessToken()).thenReturn("test-token");

    // Test ttl >= 600
    jwtOAuth = JWTOAuth.builder().jwtClient(jwtClient).ttl(600).build();
    when(jwtClient.getAccessToken((Integer) any(), any(), any())).thenReturn(oAuthToken);
    jwtOAuth.token();
    verify(jwtClient).getAccessToken(eq(600), any(), any());

    // Test 60 <= ttl < 600
    jwtOAuth = JWTOAuth.builder().jwtClient(jwtClient).ttl(100).build();
    when(jwtClient.getAccessToken((Integer) any(), any(), any())).thenReturn(oAuthToken);
    jwtOAuth.token();
    verify(jwtClient).getAccessToken(eq(100), any(), any());

    // Test 30 <= ttl < 60
    jwtOAuth = JWTOAuth.builder().jwtClient(jwtClient).ttl(30).build();
    when(jwtClient.getAccessToken((Integer) any(), any(), any())).thenReturn(oAuthToken);
    jwtOAuth.token();
    verify(jwtClient).getAccessToken(eq(30), any(), any());
  }
}
