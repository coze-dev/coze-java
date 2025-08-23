package com.coze.openapi.client.audio.rooms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslateConfig {
  @JsonProperty("from")
  private String from;

  @JsonProperty("to")
  private String to;
}
