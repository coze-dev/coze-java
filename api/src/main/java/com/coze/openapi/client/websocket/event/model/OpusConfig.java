package com.coze.openapi.client.websocket.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OpusConfig {
  @JsonProperty("bitrate")
  private Integer bitrate;

  @JsonProperty("use_cbr")
  private Boolean useCbr;

  @JsonProperty("frame_size_ms")
  private Double frameSizeMs;

  @JsonProperty("limit_config")
  private LimitConfig limitConfig;
}
