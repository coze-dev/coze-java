package com.coze.openapi.client.workspace;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkspaceType {
    PERSONAL("personal"),
    TEAM("team");

    @JsonValue  // 序列化时使用这个值
    private final String value;

    WorkspaceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator  // 反序列化时使用这个方法
    public static WorkspaceType fromString(String value) {
        for (WorkspaceType type : WorkspaceType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown message type: " + value);
    }
} 