package com.coze.openapi.client.knowledge.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DocumentSourceType {
    LOCAL_FILE(0),
    ONLINE_WEB(1);

    private final int value;

    DocumentSourceType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static DocumentSourceType fromValue(int value) {
        for (DocumentSourceType type : DocumentSourceType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown DocumentSourceType value: " + value);
    }
} 