package com.coze.openapi.service.service.websocket.chat;

import com.coze.openapi.client.common.BaseReq;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WsChatReq extends BaseReq {
  @NonNull private String botID;
  @NonNull private WebsocketChatCallbackHandler callbackHandler;
}
