/* (C)2024 */
package com.coze.openapi.client.chat.model;

import java.util.Map;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.exception.CozeApiExcetion;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChatEvent extends BaseResp {
  @JsonProperty("event")
  private ChatEventType event;

  @JsonProperty("chat")
  private Chat chat;

  @JsonProperty("message")
  private Message message;

  public static ChatEvent parseEvent(Map<String, String> eventLine, String logID) {
    ChatEventType eventType = ChatEventType.fromString(eventLine.get("event"));
    String data = eventLine.get("data");
    if (ChatEventType.DONE.equals(eventType)) {
      return ChatEvent.builder().event(eventType).logID(logID).build();
    }
    if (ChatEventType.ERROR.equals(eventType)) {
      throw new CozeApiExcetion(0, data, logID);
    }
    if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(eventType)
        || ChatEventType.CONVERSATION_MESSAGE_COMPLETED.equals(eventType)
        || ChatEventType.CONVERSATION_AUDIO_DELTA.equals(eventType)) {
      return ChatEvent.builder()
          .event(eventType)
          .message(Message.fromJson(data))
          .logID(logID)
          .build();
    }
    if (ChatEventType.CONVERSATION_CHAT_CREATED.equals(eventType)
        || ChatEventType.CONVERSATION_CHAT_IN_PROGRESS.equals(eventType)
        || ChatEventType.CONVERSATION_CHAT_COMPLETED.equals(eventType)
        || ChatEventType.CONVERSATION_CHAT_FAILED.equals(eventType)
        || ChatEventType.CONVERSATION_CHAT_REQUIRES_ACTION.equals(eventType)) {
      return ChatEvent.builder().event(eventType).chat(Chat.fromJson(data)).logID(logID).build();
    }
    return ChatEvent.builder().event(eventType).logID(logID).build();
  }

  public boolean isDone() {
    return ChatEventType.DONE.equals(this.event) || ChatEventType.ERROR.equals(this.event);
  }
}
