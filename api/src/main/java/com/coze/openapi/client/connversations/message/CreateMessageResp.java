package com.coze.openapi.client.connversations.message;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.connversations.message.model.Message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CreateMessageResp extends BaseResp {
    private Message message;
}
