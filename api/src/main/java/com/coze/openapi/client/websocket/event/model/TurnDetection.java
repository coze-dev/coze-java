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
  private String type = "client_interrupt";

  @JsonProperty("prefix_padding_ms")
  private Integer prefixPaddingMs;

  @JsonProperty("silence_duration_ms")
  private Integer silenceDurationMs;

  @JsonProperty("interrupt_config")
  private InterruptConfig interruptConfig;

  @JsonProperty("asr_config")
  private AsrConfig asrConfig;
}
