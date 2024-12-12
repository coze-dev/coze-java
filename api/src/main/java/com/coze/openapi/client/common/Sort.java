package com.coze.openapi.client.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Sort {
    DESC("desc"),
    // Indicates that the content of the message is sent by the user.
    ASC("asc");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
