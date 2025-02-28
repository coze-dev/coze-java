package com.coze.openapi.client.websocket.event.upstream;

import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
// event_type: input_audio_buffer.append
public class InputAudioBufferAppendEvent extends BaseEvent {
  @JsonProperty("event_type")
  @Builder.Default
  private final String eventType = EventType.INPUT_AUDIO_BUFFER_APPEND;

  @JsonProperty("data")
  private Data data;

  @NoArgsConstructor
  @AllArgsConstructor
  public static class Data {
    @JsonProperty("delta")
    private String delta;
  }

  public static InputAudioBufferAppendEvent of(String delta) {
    return builder().data(new Data(delta)).build();
  }
}
