package com.coze.openapi.client.websocket.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TurnDetection {
  @Builder.Default
  @JsonProperty("type")
  private String type = "client_vad";

  @JsonProperty("prefix_padding_ms")
  private Integer prefixPaddingMs;

  @JsonProperty("suffix_padding_ms")
  private Integer silenceDurationMs;
}
