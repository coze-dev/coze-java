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
public class RetrieveChatReq {

    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;
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
