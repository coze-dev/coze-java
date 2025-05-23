package com.coze.openapi.client.websocket.event.upstream;

import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.coze.openapi.client.websocket.event.model.TranscriptionsUpdateEventData;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
// 发送转录配置更新事件
// event_type: transcriptions.update
public class TranscriptionsUpdateEvent extends BaseEvent {
  @JsonProperty("event_type")
  @Builder.Default
  private final String eventType = EventType.TRANSCRIPTIONS_UPDATE;

  @JsonProperty("data")
  private TranscriptionsUpdateEventData data;
}
