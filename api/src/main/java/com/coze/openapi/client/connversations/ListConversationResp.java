package com.coze.openapi.client.connversations;

import java.util.List;

import com.coze.openapi.client.connversations.model.Conversation;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListConversationResp {
    @JsonProperty("has_more")
    private boolean hasMore;

    @JsonProperty("conversations")
    private List<Conversation> conversations;
}
