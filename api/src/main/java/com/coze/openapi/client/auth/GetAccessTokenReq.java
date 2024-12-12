package com.coze.openapi.client.auth;

import com.coze.openapi.client.auth.scope.Scope;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAccessTokenReq {
    @JsonProperty("client_id")
    private String clientID;

    @JsonProperty("code")
    private String code;

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("redirect_uri")
    private String redirectUri;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("code_verifier")
    private String codeVerifier;

    @JsonProperty("device_code")
    private String deviceCode;

    @JsonProperty("duration_seconds")
    private Integer durationSeconds;

    @JsonProperty("scope")
    private Scope scope;
}
