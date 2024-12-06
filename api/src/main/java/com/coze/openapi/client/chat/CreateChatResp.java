package com.coze.openapi.client.chat;

import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.common.BaseResp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateChatResp extends BaseResp{
    private Chat chat;
}
