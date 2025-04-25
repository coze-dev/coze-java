package com.coze.openapi.service.auth;

import java.util.Objects;

import com.coze.openapi.client.auth.GetJWTAccessTokenReq;
import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.auth.model.SessionContext;
import com.coze.openapi.client.auth.scope.Scope;

import lombok.*;

@Getter
@Builder(builderClassName = "OAuthJWTBuilder")
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class JWTOAuth extends Auth {

  private Integer ttl;
  private String sessionName;
  private Scope scope;
  private String enterpriseID;
  private Long accountID;
  private SessionContext sessionContext;

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
    GetJWTAccessTokenReq req =
        GetJWTAccessTokenReq.builder()
            .enterpriseID(enterpriseID)
            .scope(scope)
            .ttl(ttl)
            .sessionName(sessionName)
            .sessionContext(sessionContext)
            .accountID(accountID)
            .build();
    OAuthToken resp = this.jwtClient.getAccessToken(req);
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

  public static class OAuthJWTBuilder {
    public JWTOAuth build() {
      JWTOAuth jwt = new JWTOAuth();
      if (this.jwtClient == null) {
        throw new IllegalStateException("jwtClient is required");
      }
      jwt.setJwtClient(this.jwtClient);
      if (this.ttl != null) {
        jwt.ttl = this.ttl;
      } else {
        jwt.setTtl(this.jwtClient.getTtl());
      }
      jwt.setSessionName(this.sessionName);
      jwt.setSessionContext(this.sessionContext);
      jwt.setAccountID(this.accountID);
      jwt.setEnterpriseID(this.enterpriseID);
      jwt.setScope(this.scope);
      return jwt;
    }
  }
}
