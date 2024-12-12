package com.coze.openapi.client.workflows.run.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Execution status of the workflow.
 */
public enum WorkflowExecuteStatus {
    /**
     * Execution succeeded.
     */
    SUCCESS("Success"),

    /**
     * Execution in progress.
     */
    RUNNING("Running"),

    /**
     * Execution failed.
     */
    FAIL("Fail");

    private final String value;

    WorkflowExecuteStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static WorkflowExecuteStatus fromString(String value) {
        for (WorkflowExecuteStatus status : WorkflowExecuteStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown WorkflowExecuteStatus: " + value);
    }
} 