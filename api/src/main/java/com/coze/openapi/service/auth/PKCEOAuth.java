package com.coze.openapi.service.auth;

import java.util.Objects;

public class PKCEOAuth extends Auth {

    public PKCEOAuth(PKCEOAuthClient client, String refreshToken, String redirectURI) {
        Objects.requireNonNull(client, "client must not be null");
        this.client = client;
        this.refreshToken = refreshToken;
        this.redirectURI = redirectURI;
    }
}
