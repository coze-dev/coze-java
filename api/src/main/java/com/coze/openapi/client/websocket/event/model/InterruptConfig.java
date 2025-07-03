package com.coze.openapi.client.websocket.event.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InterruptConfig {
  @JsonProperty("mode")
  private String mode;

  @JsonProperty("keywords")
  private List<String> keywords;
}
