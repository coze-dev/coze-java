package com.coze.openapi.client.chat.message;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListMessageReq extends BaseReq {
  @NonNull
  @JsonProperty("conversation_id")
  private String conversationID;

  @NonNull
  @JsonProperty("chat_id")
  private String chatID;

  private ListMessageReq(String conversationID, String chatID) {
    this.conversationID = conversationID;
    this.chatID = chatID;
  }

  public static ListMessageReq of(String conversationID, String chatID) {
    return ListMessageReq.builder().conversationID(conversationID).chatID(chatID).build();
  }
}
