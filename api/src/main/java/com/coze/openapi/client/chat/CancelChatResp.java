package com.coze.openapi.client.chat;

import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.common.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CancelChatResp extends BaseResp{
    private Chat chat;
    
}
