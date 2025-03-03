package com.coze.openapi.client.websocket.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LimitConfig {
  @JsonProperty("period")
  private Integer period;

  @JsonProperty("max_frame_num")
  private Integer maxFrameNum;
}
