package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotVariable {
  @JsonProperty("enable")
  private Boolean enable;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("keyword")
  private String keyword;

  @JsonProperty("description")
  private String description;

  @JsonProperty("default_value")
  private String defaultValue;

  @JsonProperty("prompt_enable")
  private Boolean promptEnable;
}
