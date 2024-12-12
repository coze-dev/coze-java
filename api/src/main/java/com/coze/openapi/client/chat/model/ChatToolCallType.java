package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ChatToolCallType {
    FUNCTION("function");

    private final String value;

    ChatToolCallType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ChatToolCallType fromString(String value) {
        for (ChatToolCallType type : ChatToolCallType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ChatToolCallType: " + value);
    }
} 