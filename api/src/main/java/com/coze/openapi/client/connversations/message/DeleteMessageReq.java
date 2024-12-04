package com.coze.openapi.client.connversations.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMessageReq {
    /*
     * The ID of the conversation.
     * */
    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;

    /*
     * message id
     * */
    @NonNull
    @JsonProperty("message_id")
    private String messageID;

    public static DeleteMessageReq of(String conversationID, String messageID) {
        return DeleteMessageReq.builder().conversationID(conversationID).messageID(messageID).build();
    }
}
