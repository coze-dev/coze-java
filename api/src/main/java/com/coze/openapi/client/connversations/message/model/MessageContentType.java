package com.coze.openapi.client.connversations.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageContentType {
    UNKNOWN("unknown"),
    // Text.
    TEXT("text"),

    // Multimodal content, that is, a combination of text and files, or a combination of text and images.
    OBJECT_STRING("object_string"),

    // Message card. This enum value only appears in the interface response and is not supported as an input parameter.
    CARD("card"),

    // If there is a voice message in the input message, the conversation.audio.delta event will be returned in the streaming response event.
    AUDIO("audio");

    private final String value;

    MessageContentType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator  // 反序列化时使用这个方法
    public static MessageContentType fromString(String value) {
        for (MessageContentType type : MessageContentType.values()) {
            if (type.value.equals(value)) {
                    return type;
                }
        }
        return UNKNOWN;
    }
} 