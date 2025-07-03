package com.coze.openapi.client.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceInfo {
  @JsonProperty("device_id")
  private String deviceID;

  @JsonProperty("custom_consumer")
  private String customConsumer;
}
