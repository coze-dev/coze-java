package com.coze.openapi.client.connversations.message;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateMessageResp extends BaseResponse<Message> {
    @JsonProperty("message")
    private Message message;
    
    
}
