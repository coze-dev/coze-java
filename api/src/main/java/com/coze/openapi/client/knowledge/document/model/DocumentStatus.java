package com.coze.openapi.client.knowledge.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DocumentStatus {
    PROCESSING(0),
    COMPLETED(1),
    FAILED(9);

    private final int value;

    DocumentStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static DocumentStatus fromValue(int value) {
        for (DocumentStatus status : DocumentStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown DocumentStatus value: " + value);
    }
} 