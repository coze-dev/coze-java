package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BotMode {
    SINGLE_AGENT(0),
    MULTI_AGENT(1),
    SINGLE_AGENT_WORKFLOW(2);

    private final int value;

    BotMode(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static BotMode fromValue(int value) {
        for (BotMode mode : BotMode.values()) {
            if (mode.value == value) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown BotMode value: " + value);
    }
} 