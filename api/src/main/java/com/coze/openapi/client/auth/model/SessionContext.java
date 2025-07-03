package com.coze.openapi.client.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionContext {
  @JsonProperty("device_info")
  private DeviceInfo deviceInfo;
}
