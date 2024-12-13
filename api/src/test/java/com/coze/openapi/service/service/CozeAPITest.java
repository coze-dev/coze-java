/* (C)2024 */
package com.coze.openapi.service.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.coze.openapi.service.auth.Auth;

import okhttp3.OkHttpClient;

public class CozeAPITest {

  @Mock private Auth auth;

  @Mock private Logger logger;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testBuildWithDefaultSettings() {
    CozeAPI api = new CozeAPI.Builder().auth(auth).build();

    assertNotNull(api);
    assertNotNull(api.workspaces());
    assertNotNull(api.bots());
    assertNotNull(api.conversations());
    assertNotNull(api.files());
    assertNotNull(api.datasets());
    assertNotNull(api.workflows());
    assertNotNull(api.chat());
    assertNotNull(api.audio());
  }

  @Test
  public void testBuildWithCustomSettings() {
    String customBaseUrl = "https://custom.api.coze.com";
    int customReadTimeout = 10000;
    int customConnectTimeout = 8000;

    CozeAPI api =
        new CozeAPI.Builder()
            .auth(auth)
            .baseURL(customBaseUrl)
            .readTimeout(customReadTimeout)
            .connectTimeout(customConnectTimeout)
            .logger(logger)
            .build();

    assertNotNull(api);
  }

  @Test
  void testBuildWithoutAuth() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          new CozeAPI.Builder().build();
        });
  }

  @Test
  public void testCustomHttpClient() {
    OkHttpClient customClient =
        new OkHttpClient.Builder().readTimeout(Duration.ofSeconds(30)).build();

    CozeAPI api = new CozeAPI.Builder().auth(auth).client(customClient).build();

    assertNotNull(api);
  }
}
