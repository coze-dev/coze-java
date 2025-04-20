package com.coze.openapi.service.auth;

import java.util.Objects;

import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.auth.scope.Scope;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;

@Builder
@AllArgsConstructor
public class JWTOAuth extends Auth {

  private Integer ttl;
  private String sessionName;
  private Scope scope;
  @NonNull private JWTOAuthClient jwtClient;

  public JWTOAuth(JWTOAuthClient client) {
    Objects.requireNonNull(client, "client must not be null");
    this.jwtClient = client;
    this.ttl = client.getTtl();
  }

  protected boolean needRefresh() {
    return accessToken == null || System.currentTimeMillis() / 1000 > refreshAt;
  }

  public String token() {
    if (!this.needRefresh()) {
      return accessToken;
    }
    OAuthToken resp = this.jwtClient.getAccessToken(this.ttl, this.scope, this.sessionName);
    this.accessToken = resp.getAccessToken();
    this.expiresIn = resp.getExpiresIn();
    this.refreshAt = this.expiresIn - getRefreshBefore();
    return this.accessToken;
  }

  private long getRefreshBefore() {
    if (ttl >= 600) {
      return 30;
    } else if (ttl >= 60) {
      return 10;
    } else if (ttl >= 30) {
      return 5;
    }
    return 0;
  }
}
