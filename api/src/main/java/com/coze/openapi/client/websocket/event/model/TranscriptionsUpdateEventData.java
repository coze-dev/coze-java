package com.coze.openapi.client.websocket.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TranscriptionsUpdateEventData {
  @JsonProperty("input_audio")
  private InputAudio inputAudio;
}
