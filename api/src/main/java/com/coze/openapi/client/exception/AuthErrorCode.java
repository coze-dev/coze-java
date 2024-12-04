package com.coze.openapi.client.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    UnknownError("unknown_error"),
    /*
     * The user has not completed authorization yet, please try again later
    */
    AuthorizationPending("authorization_pending"),
    /*
     * The request is too frequent, please try again later
    */
    SlowDown("slow_down"),
    /*
     * The user has denied the authorization
    */
    AccessDenied("access_denied"),
    /*
     * The token is expired
    */
    ExpiredToken("expired_token"),
    /*
     * The request param is invalid
    */
    InvalidRequest("invalid_request"),
    /*
     * The client credentials (JWT Token or Client Secret) are invalid
    */
    InvalidClient("invalid_client"),
    /*
     * The grant type is not supported
    */
    UnsupportedGrantType("unsupported_grant_type"),
    /*
     * This exception caused by three possibilities:
     * 
     * 1. The OAuth application has been disabled.
     * 2. The type of APP is incorrect.
     * 3. User login status is invalid
    */
    AccessDeny("access_deny"),
    /*
     * Server internal error, you can try again later
    */
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