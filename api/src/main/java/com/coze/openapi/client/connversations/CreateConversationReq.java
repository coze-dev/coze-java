package com.coze.openapi.client.connversations;

import java.util.List;
import java.util.Map;

import com.coze.openapi.client.connversations.message.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
public class CreateConversationReq {
    @JsonProperty("messages")   
    private List<Message> messages;

    @JsonProperty("meta_data")
    private Map<String, String> metaData;
}
