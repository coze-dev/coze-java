package com.coze.openapi.client.websocket.event.downstream;

import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
// 增量语音新句子开始
// event_type: conversation.audio.sentence_start
public class ConversationAudioSentenceStartEvent extends BaseEvent {
  @JsonProperty("event_type")
  @Builder.Default
  private final String eventType = EventType.CONVERSATION_AUDIO_SENTENCE_START;

  @JsonProperty("data")
  private ConversationAudioSentenceStartEvent.Data data;

  @lombok.Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Data {
    @JsonProperty("text")
    private String text;
  }
}
