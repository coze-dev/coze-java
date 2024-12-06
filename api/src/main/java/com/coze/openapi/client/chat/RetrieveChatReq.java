package com.coze.openapi.client.chat;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetrieveChatReq extends BaseReq {
    /*
     *  The Conversation ID can be viewed in the 'conversation_id' field of the Response when
     *  initiating a conversation through the Chat API.
     * */
    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;

    /*
     * The Chat ID can be viewed in the 'id' field of the Response when initiating a chat through the
     *  Chat API. If it is a streaming response, check the 'id' field in the chat event of the Response.
     * */
    @NonNull
    @JsonProperty("chat_id")
    private String chatID;

    private RetrieveChatReq(String conversationID, String chatID) {
        this.conversationID = conversationID;
        this.chatID = chatID;
    }

    public static RetrieveChatReq of(String conversationID, String chatID) {
        return RetrieveChatReq.builder()
            .conversationID(conversationID)
            .chatID(chatID)
            .build();
    }
}
