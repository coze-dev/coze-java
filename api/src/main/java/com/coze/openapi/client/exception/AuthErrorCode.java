package com.coze.openapi.client.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    UnknownError("unknown_error"),
    AuthorizationPending("authorization_pending"),
    SlowDown("slow_down"),
    AccessDenied("access_denied"),
    ExpiredToken("expired_token"),
    InvalidRequest("invalid_request"),
    InvalidClient("invalid_client"),
    UnsupportedGrantType("unsupported_grant_type"),
    AccessDeny("access_deny"),
    InternalError("internal_error");


    private final String value;

    @Override
    public String toString() {
        return this.value;
    }

    public static AuthErrorCode fromString(String value) {
        for (AuthErrorCode errorCode : AuthErrorCode.values()) {
            if (errorCode.value.equals(value)) {
                return errorCode;
            }
        }
        return UnknownError;
    }
}