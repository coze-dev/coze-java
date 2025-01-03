package com.coze.openapi.client.connversations.message;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeleteMessageReq extends BaseReq {
  /*
   * The ID of the conversation.
   * */
  @NonNull
  @JsonProperty("conversation_id")
  private String conversationID;

  /*
   * message id
   * */
  @NonNull
  @JsonProperty("message_id")
  private String messageID;

  public static DeleteMessageReq of(String conversationID, String messageID) {
    return DeleteMessageReq.builder().conversationID(conversationID).messageID(messageID).build();
  }
}
