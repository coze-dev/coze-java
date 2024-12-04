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
public class RetrieveConversationReq {
    /*
    * The ID of the conversation.
    * */
    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;

    public static RetrieveConversationReq of(String conversationID) {
        return RetrieveConversationReq.builder().conversationID(conversationID).build();
    }
}
