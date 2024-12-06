package com.coze.openapi.client.workflows.run.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Workflow event types.
 */
@Getter
@AllArgsConstructor
public class WorkflowEventType {
    /**
     * The output message from the workflow node, such as the output message from
     * the message node or end node. You can view the specific message content in data.
     */
    public static final WorkflowEventType MESSAGE = new WorkflowEventType("Message");

    /**
     * An error has occurred. You can view the error_code and error_message in data to
     * troubleshoot the issue.
     */
    public static final WorkflowEventType ERROR = new WorkflowEventType("Error");

    /**
     * End. Indicates the end of the workflow execution, where data is empty.
     */
    public static final WorkflowEventType DONE = new WorkflowEventType("Done");

    /**
     * Interruption. Indicates the workflow has been interrupted, where the data field
     * contains specific interruption information.
     */
    public static final WorkflowEventType INTERRUPT = new WorkflowEventType("Interrupt");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static WorkflowEventType fromString(String value) {
        if (value == null) {
            return new WorkflowEventType("");
        }
        
        if (value.equals(MESSAGE.getValue())) return MESSAGE;
        if (value.equals(ERROR.getValue())) return ERROR;
        if (value.equals(DONE.getValue())) return DONE;
        if (value.equals(INTERRUPT.getValue())) return INTERRUPT;
        
        return new WorkflowEventType(value);
    }
} 