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
public class ChatError {
    /**
     * The error code. An integer type. 0 indicates success, other values indicate failure.
     */
    @JsonProperty("code")
    private int code;

    /**
     * The error message. A string type.
     */
    @JsonProperty("msg")
    private String msg;
} 