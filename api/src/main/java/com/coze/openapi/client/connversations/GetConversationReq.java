package com.coze.openapi.client.connversations;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetConversationReq {
    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;

    public static GetConversationReq of(String conversationID) {
        return GetConversationReq.builder().conversationID(conversationID).build();
    }
}
