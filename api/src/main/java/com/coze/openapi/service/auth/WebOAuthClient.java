package com.coze.openapi.service.auth;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.client.auth.GrantType;
import com.coze.openapi.client.auth.LoadAuthConfig;
import com.coze.openapi.client.auth.OAuthConfig;
import com.coze.openapi.client.auth.OAuthToken;

public class WebOAuthClient extends OAuthClient {

  protected WebOAuthClient(OAuthBuilder builder) {
    super(builder);
  }

  public static WebOAuthClient loadFromConfig(LoadAuthConfig loadConfig) {
    OAuthConfig config = OAuthConfig.load(loadConfig);
    return new WebOAuthClient.WebOAuthBuilder()
        .clientID(config.getClientId())
        .clientSecret(config.getClientSecret())
        .baseURL(config.getCozeApiBase())
        .build();
  }

  @Override
  public String getOAuthURL(@NotNull String redirectURI, String state) {
    return super.getOAuthURL(redirectURI, state);
  }

  @Override
  public String getOAuthURL(
      @NotNull String redirectURI, String state, @NotNull String workspaceID) {
    return super.getOAuthURL(redirectURI, state, workspaceID);
  }

  public OAuthToken getAccessToken(String code, String redirectURI) {
    return super.getAccessToken(GrantType.AUTHORIZATION_CODE, code, this.clientSecret, redirectURI);
  }

  @Override
  public OAuthToken refreshToken(String refreshToken) {
    return super.refreshAccessToken(refreshToken, this.clientSecret);
  }

  public static class WebOAuthBuilder extends OAuthBuilder<WebOAuthBuilder> {
    @Override
    protected WebOAuthBuilder self() {
      return this;
    }

    @Override
    public WebOAuthClient build() {
      return new WebOAuthClient(this);
    }
  }
}
