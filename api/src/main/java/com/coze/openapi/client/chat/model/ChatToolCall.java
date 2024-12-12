package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatToolCall {
    /**
     * The ID for reporting the running results.
     */
    @JsonProperty("id")
    private String id;

    /**
     * The type of tool, with the enum value of function.
     */
    @JsonProperty("type")
    private ChatToolCallType type;

    /**
     * The definition of the execution method function.
     */
    @JsonProperty("function")
    private ChatToolCallFunction function;
} 