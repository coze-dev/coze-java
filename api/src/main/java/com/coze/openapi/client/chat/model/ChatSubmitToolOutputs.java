package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSubmitToolOutputs {
    /**
     * Details of the specific reported information.
     */
    @JsonProperty("tool_calls")
    private List<ChatToolCall> toolCalls;
} 