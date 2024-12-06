package com.coze.openapi.client.connversations.message;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.connversations.message.model.Message;

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
public class RetrieveMessageResp extends BaseResp {
    private Message message;
}




