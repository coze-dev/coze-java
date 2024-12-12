/* (C)2024 */
package com.coze.openapi.client.connversations.message;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.connversations.message.model.Message;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RetrieveMessageResp extends BaseResp {
  private Message message;
}
