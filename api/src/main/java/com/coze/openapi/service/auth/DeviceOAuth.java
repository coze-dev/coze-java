package com.coze.openapi.service.auth;

import java.util.Objects;

public class DeviceOAuth extends Auth {

    public DeviceOAuth(DeviceOAuthClient client, String refreshToken, String redirectURI) {
        Objects.requireNonNull(client, "client must not be null");
        this.client = client;
        this.refreshToken = refreshToken;
        this.redirectURI = redirectURI;
    }
}
