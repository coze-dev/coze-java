package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ChatRequiredActionType {
    UNKNOWN(""),
    SUBMIT_TOOL_OUTPUTS("submit_tool_outputs");

    private final String value;

    ChatRequiredActionType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ChatRequiredActionType fromString(String value) {
        for (ChatRequiredActionType type : ChatRequiredActionType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ChatRequiredActionType: " + value);
    }
} 