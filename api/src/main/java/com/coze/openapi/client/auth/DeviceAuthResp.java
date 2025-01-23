package com.coze.openapi.client.auth;

import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceAuthResp extends BaseResp {
  @JsonProperty("device_code")
  private String deviceCode;

  @JsonProperty("user_code")
  private String userCode;

  @JsonProperty("verification_uri")
  private String verificationURI;

  @JsonProperty("verification_url")
  private String verificationURL;

  @JsonProperty("expires_in")
  private int expiresIn;

  @JsonProperty("interval")
  private int interval;
}
