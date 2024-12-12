package com.coze.openapi.client.workspace;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkspaceRoleType {
    OWNER("owner"),
    ADMIN("admin"),
    MEMBER("member");


    WorkspaceRoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonValue  // 序列化时使用这个值
    private final String value;

    @JsonCreator  // 反序列化时使用这个方法
    public static WorkspaceRoleType fromString(String value) {
        for (WorkspaceRoleType type : WorkspaceRoleType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown message type: " + value);
    }
} 