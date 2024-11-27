package com.coze.openapi.service.auth;

import java.security.PrivateKey;
import java.util.Objects;

import com.coze.openapi.client.auth.GetAccessTokenResp;
import com.coze.openapi.client.auth.scope.Scope;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;


@Builder
@AllArgsConstructor
public class JWTOauth extends Auth {

    private Integer ttl;
    private String sessionName;
    private Scope scope;
    @NonNull
    private JWTOAuthClient jwtClient;

    public JWTOauth(JWTOAuthClient client) {
        Objects.requireNonNull(client, "client must not be null");
        this.jwtClient = client;
        this.ttl = client.getTtl();
    }

    @Override
    public String token(){
         if (!this.needRefresh()) {
            return accessToken;
        }
        GetAccessTokenResp resp = this.jwtClient.getAccessToken(this.ttl, this.scope, this.sessionName);
        this.accessToken = resp.getAccessToken();
        this.refreshToken = resp.getRefreshToken();
        this.expiresIn = resp.getExpiresIn();
        return this.accessToken;
    }

    
}
