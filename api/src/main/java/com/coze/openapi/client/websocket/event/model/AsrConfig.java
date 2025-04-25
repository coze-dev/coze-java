package com.coze.openapi.client.websocket.event.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AsrConfig {
  @JsonProperty("hot_words")
  private List<String> hotWords;

  @JsonProperty("context")
  private String context;

  @JsonProperty("user_language")
  private String userLanguage;
}
