package com.coze.openapi.client.auth;

import com.coze.openapi.client.auth.scope.Scope;
import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetAccessTokenReq extends BaseReq {
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
