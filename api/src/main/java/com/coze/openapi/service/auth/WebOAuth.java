package com.coze.openapi.service.auth;

import java.util.Objects;

public class WebOAuth extends Auth {
    public WebOAuth(WebOAuthClient client, String refreshToken, String redirectURI) {
        Objects.requireNonNull(client, "client must not be null");
        this.client = client;
        this.refreshToken = refreshToken;
        this.redirectURI = redirectURI;
    }
}
