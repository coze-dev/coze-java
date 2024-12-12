/* (C)2024 */
package com.coze.openapi.client.connversations;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.connversations.model.Conversation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveConversationResp extends BaseResp {
  private Conversation conversation;
}
