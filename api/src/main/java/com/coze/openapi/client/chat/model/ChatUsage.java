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
public class ChatUsage {
    /**
     * The total number of Tokens consumed in this chat, including the consumption for both the input
     * and output parts.
     */
    @JsonProperty("token_count")
    private int tokenCount;

    /**
     * The total number of Tokens consumed for the output part.
     */
    @JsonProperty("output_count")
    private int outputCount;

    /**
     * The total number of Tokens consumed for the input part.
     */
    @JsonProperty("input_count")
    private int inputCount;
} 