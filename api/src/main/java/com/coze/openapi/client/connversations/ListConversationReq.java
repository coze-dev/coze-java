package com.coze.openapi.client.connversations;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListConversationReq {
    /**
     * The ID of the bot.
     */
    @NonNull
    @JsonProperty("bot_id")
    private String botID;

    /**
     * The page number.
     */
    @JsonProperty("page_num")
    private Integer pageNum;

    /**
     * The page size.
     */
    @JsonProperty("page_size")
    private Integer pageSize;
}
