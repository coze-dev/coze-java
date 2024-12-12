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
    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;
    @NonNull
    @JsonProperty("message_id")
    private String messageID;
}
