package com.coze.openapi.client.connversations;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClearConversationResp {
    /**
     * The ID of the conversation.
     */
    private String id;

    /**
     * The ID of the conversation.
     */
    private String conversationID;
}
