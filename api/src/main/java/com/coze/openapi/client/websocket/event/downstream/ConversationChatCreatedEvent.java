package com.coze.openapi.client.websocket.event.downstream;

import com.coze.openapi.client.chat.model.Chat;
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
// 对话创建事件
// event_type: conversation.chat.created
public class ConversationChatCreatedEvent extends BaseEvent {
  @JsonProperty("event_type")
  @Builder.Default
  private final String eventType = EventType.CONVERSATION_CHAT_CREATED;

  @JsonProperty("data")
  private Chat data;
}
