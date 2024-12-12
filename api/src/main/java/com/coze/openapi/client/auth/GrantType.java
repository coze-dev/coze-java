package com.coze.openapi.client.auth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GrantType {
    AuthorizationCode("authorization_code"),
    DeviceCode("urn:ietf:params:oauth:grant-type:device_code"),
    JWTCode("urn:ietf:params:oauth:grant-type:jwt-bearer"),
    RefreshToken("refresh_token");

    private final String value;

    @Override
    public String toString() {
        return this.value;
    }
}
