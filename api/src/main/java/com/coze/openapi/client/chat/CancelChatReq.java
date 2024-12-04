package com.coze.openapi.client.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Getter
@Builder
@NoArgsConstructor
public class CancelChatReq {

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

    private CancelChatReq(String conversationID, String chatID) {
        this.conversationID = conversationID;
        this.chatID = chatID;
    }

    public static CancelChatReq of(String conversationID, String chatID) {
        return CancelChatReq.builder()
            .conversationID(conversationID)
            .chatID(chatID)
            .build();
    }
}
