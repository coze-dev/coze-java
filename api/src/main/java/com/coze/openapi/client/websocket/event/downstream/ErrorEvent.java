package com.coze.openapi.client.websocket.event.downstream;

import com.coze.openapi.client.exception.CozeApiException;
import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
// 发生异常
// event_type: error
public class ErrorEvent extends BaseEvent {
  @JsonProperty("event_type")
  @Builder.Default
  private final String eventType = EventType.ERROR;

  @JsonProperty("data")
  private CozeApiException data;
}
