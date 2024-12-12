package com.coze.openapi.client.workflows.run.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowEvent {
    /**
     * The event ID of this message in the interface response. It starts from 0.
     */
    @JsonProperty("id")
    private int id;

    /**
     * The current streaming data packet event.
     */
    @JsonProperty("event")
    private WorkflowEventType event;

    @JsonProperty("message")
    private WorkflowEventMessage message;

    @JsonProperty("interrupt")
    private WorkflowEventInterrupt interrupt;

    @JsonProperty("error")
    private WorkflowEventError error;

    private static WorkflowEvent parseWorkflowEventMessage(Integer id, String data) {
        WorkflowEventMessage message = WorkflowEventMessage.fromJson(data);
        return new WorkflowEvent(id, WorkflowEventType.MESSAGE, message, null, null);
    }

    private static WorkflowEvent parseWorkflowEventInterrupt(Integer id, String data) {
        WorkflowEventInterrupt interrupt = WorkflowEventInterrupt.fromJson(data);
        return new WorkflowEvent(id, WorkflowEventType.INTERRUPT, null, interrupt, null);
    }

    private static WorkflowEvent parseWorkflowEventError(Integer id, String data) {
        WorkflowEventError error = WorkflowEventError.fromJson(data);
        return new WorkflowEvent(id, WorkflowEventType.ERROR, null, null, error);
    }

    private static WorkflowEvent parseWorkflowEventDone(Integer id) {
        return new WorkflowEvent(id, WorkflowEventType.DONE, null, null, null);
    }

    public static WorkflowEvent parseEvent(Map<String, String> eventLine) {
        Integer id = Integer.parseInt(eventLine.get("id"));
        WorkflowEventType event = WorkflowEventType.fromString(eventLine.get("event"));
        String data = eventLine.get("data");

        switch (event) {
            case MESSAGE:
                return parseWorkflowEventMessage(id, data);
            case INTERRUPT:
                return parseWorkflowEventInterrupt(id, data);
            case ERROR:
                return parseWorkflowEventError(id, data);
            case DONE:
                return parseWorkflowEventDone(id);
            default:
                break;
        }
        return null;
    }

    public boolean isDone() {
        return !WorkflowEventType.MESSAGE.equals(this.event);
    }
} 
