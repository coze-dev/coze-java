package com.coze.openapi.service.auth;

import com.coze.openapi.client.auth.GetAccessTokenResp;

import java.util.Objects;

public class WebOAuth extends Auth {
    public WebOAuth(WebOAuthClient client, String code, String redirectURI) {
        Objects.requireNonNull(client, "client must not be null");
        Objects.requireNonNull(code, "code must not be null");
        this.client = client;
        this.redirectURI = redirectURI;

        GetAccessTokenResp resp = client.getAccessToken(code, redirectURI);
        this.accessToken = resp.getAccessToken();
        this.refreshToken = resp.getRefreshToken();
        this.expiresIn = resp.getExpiresIn();
    }
}
