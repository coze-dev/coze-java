package com.coze.openapi.client.connversations;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.connversations.model.Conversation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateConversationResp extends BaseResp{

    private Conversation conversation;
}
