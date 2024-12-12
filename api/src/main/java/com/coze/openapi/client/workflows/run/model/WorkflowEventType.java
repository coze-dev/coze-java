package com.coze.openapi.client.workflows.run.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Workflow event types.
 */
public enum WorkflowEventType {
    /**
     * The output message from the workflow node, such as the output message from
     * the message node or end node. You can view the specific message content in data.
     */
    MESSAGE("Message"),

    /**
     * An error has occurred. You can view the error_code and error_message in data to
     * troubleshoot the issue.
     */
    ERROR("Error"),

    /**
     * End. Indicates the end of the workflow execution, where data is empty.
     */
    DONE("Done"),

    /**
     * Interruption. Indicates the workflow has been interrupted, where the data field
     * contains specific interruption information.
     */
    INTERRUPT("Interrupt");

    private final String value;

    WorkflowEventType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static WorkflowEventType fromString(String value) {
        for (WorkflowEventType type : WorkflowEventType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown WorkflowEventType: " + value);
    }
} 