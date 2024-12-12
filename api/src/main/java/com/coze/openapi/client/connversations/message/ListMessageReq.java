package com.coze.openapi.client.connversations.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.coze.openapi.client.common.Sort;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListMessageReq {
    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;
    @JsonProperty("chat_id")
    private String chatID;
    @JsonProperty("before_id")
    private String beforeID;
    @JsonProperty("after_id")
    private String afterID;
    @JsonProperty("limit")
    @Builder.Default
    private Integer limit = 20;
    @JsonProperty("bot_id")
    private String botID;
    @JsonProperty("order")
    @Builder.Default
    private Sort sort = Sort.DESC;

}
