package com.coze.openapi.client.websocket.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEvent {
  @JsonProperty("id")
  private String id;

  @JsonProperty("detail")
  private Detail detail;
}
