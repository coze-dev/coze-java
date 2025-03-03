package com.coze.openapi.client.websocket.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InputAudio {
  @JsonProperty("format")
  private String format;

  @JsonProperty("codec")
  private String codec;

  @JsonProperty("sample_rate")
  private Integer sampleRate;

  @JsonProperty("channel")
  private Integer channel;

  @JsonProperty("bit_depth")
  private Integer bitDepth;
}
