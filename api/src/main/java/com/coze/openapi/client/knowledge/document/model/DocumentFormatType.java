package com.coze.openapi.client.knowledge.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DocumentFormatType {
    DOCUMENT(0),
    SPREADSHEET(1),
    IMAGE(2);

    private final int value;

    DocumentFormatType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static DocumentFormatType fromValue(int value) {
        for (DocumentFormatType type : DocumentFormatType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown DocumentFormatType value: " + value);
    }
} 