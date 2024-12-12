package com.coze.openapi.client.connversations.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The content type of the multimodal message.
 */
public enum MessageObjectStringType {
    UNKNOWN("unknown"), 
    TEXT("text"),
    FILE("file"),
    IMAGE("image"),
    AUDIO("audio");

    private final String value;

    MessageObjectStringType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator  // 反序列化时使用这个方法
    public static MessageObjectStringType fromString(String value) {
        for (MessageObjectStringType type : MessageObjectStringType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return UNKNOWN;
    }
} 