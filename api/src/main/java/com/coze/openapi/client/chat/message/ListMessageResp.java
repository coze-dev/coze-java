package com.coze.openapi.client.chat.message;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.connversations.message.model.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListMessageResp extends BaseResp{
    private List<Message> messages;
}
