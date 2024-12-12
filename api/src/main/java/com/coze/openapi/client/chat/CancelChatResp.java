/* (C)2024 */
package com.coze.openapi.client.chat;

import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.common.BaseResp;

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
public class CancelChatResp extends BaseResp {
  private Chat chat;
}
