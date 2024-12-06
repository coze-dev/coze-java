package com.coze.openapi.client.connversations;

import java.util.List;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.connversations.model.Conversation;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListConversationResp extends BaseResp{
    @JsonProperty("has_more")
    private boolean hasMore;

    @JsonProperty("conversations")
    private List<Conversation> conversations;
}
