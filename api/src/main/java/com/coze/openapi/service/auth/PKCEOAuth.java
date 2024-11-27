package com.coze.openapi.service.auth;

import com.coze.openapi.client.auth.GetAccessTokenResp;

import java.util.Objects;

public class PKCEOAuth extends Auth {

    public PKCEOAuth(PKCEOAuthClient client, String code, String redirectURI, String codeVerifier) {
        Objects.requireNonNull(client, "client must not be null");
        Objects.requireNonNull(codeVerifier, "codeVerifier must not be null");
        this.client = client;
        this.redirectURI = redirectURI;

        GetAccessTokenResp resp = client.getAccessToken(code, redirectURI, codeVerifier);
        this.accessToken = resp.getAccessToken();
        this.refreshToken = resp.getRefreshToken();
        this.expiresIn = resp.getExpiresIn();
    }
}
