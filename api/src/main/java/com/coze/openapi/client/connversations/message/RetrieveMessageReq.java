/* (C)2024 */
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetrieveMessageReq extends BaseReq {
  @NonNull
  @JsonProperty("conversation_id")
  private String conversationID;

  @NonNull
  @JsonProperty("message_id")
  private String messageID;
}
