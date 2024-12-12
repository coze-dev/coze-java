package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The running status of the session.
 */
public enum ChatStatus {
    /**
     * The session has been created.
     */
    CREATED("created"),

    /**
     * The Bot is processing.
     */
    IN_PROGRESS("in_progress"),

    /**
     * The Bot has finished processing, and the session has ended.
     */
    COMPLETED("completed"),

    /**
     * The session has failed.
     */
    FAILED("failed"),

    /**
     * The session is interrupted and requires further processing.
     */
    REQUIRES_ACTION("requires_action"),

    /**
     * The session is user cancelled chat.
     */
    CANCELLED("canceled");

    private final String value;

    ChatStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ChatStatus fromString(String value) {
        for (ChatStatus status : ChatStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown ChatStatus: " + value);
    }
} 