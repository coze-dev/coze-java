/* (C)2024 */
package com.coze.openapi.client.connversations;

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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetrieveConversationReq extends BaseReq {
  /*
   * The ID of the conversation.
   * */
  @NonNull
  @JsonProperty("conversation_id")
  private String conversationID;

  public static RetrieveConversationReq of(String conversationID) {
    return RetrieveConversationReq.builder().conversationID(conversationID).build();
  }
}
