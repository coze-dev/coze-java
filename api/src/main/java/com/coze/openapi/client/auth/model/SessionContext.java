package com.coze.openapi.client.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionContext {
  @JsonProperty("device_info")
  private DeviceInfo deviceInfo;
}
