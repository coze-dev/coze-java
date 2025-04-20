package com.coze.openapi.client.websocket.event.upstream;

import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
// event_type: input_text_buffer.append
public class InputTextBufferAppendEvent extends BaseEvent {
  @JsonProperty("event_type")
  @Builder.Default
  private final String eventType = EventType.INPUT_TEXT_BUFFER_APPEND;

  @JsonProperty("data")
  private Data data;

  @NoArgsConstructor
  @AllArgsConstructor
  public static class Data {
    @JsonProperty("delta")
    private String delta;
  }

  public static InputTextBufferAppendEvent of(String delta) {
    return builder().data(new Data(delta)).build();
  }
}
