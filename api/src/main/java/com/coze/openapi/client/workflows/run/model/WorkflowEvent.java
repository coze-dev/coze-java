package com.coze.openapi.client.workflows.run.model;

import java.util.Map;

import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkflowEvent extends BaseResp {
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

    private static WorkflowEvent parseWorkflowEventMessage(Integer id, String data, String logID) {
        WorkflowEventMessage message = WorkflowEventMessage.fromJson(data);
        return WorkflowEvent.builder()
            .id(id)
            .event(WorkflowEventType.MESSAGE)
            .message(message)
            .logID(logID)
            .build();
    }

    private static WorkflowEvent parseWorkflowEventInterrupt(Integer id, String data, String logID) {
        WorkflowEventInterrupt interrupt = WorkflowEventInterrupt.fromJson(data);
        return WorkflowEvent.builder()
            .id(id)
            .event(WorkflowEventType.INTERRUPT)
            .interrupt(interrupt)
            .logID(logID)
            .build();
    }

    private static WorkflowEvent parseWorkflowEventError(Integer id, String data, String logID) {
        WorkflowEventError error = WorkflowEventError.fromJson(data);
        return WorkflowEvent.builder()
            .id(id)
            .event(WorkflowEventType.ERROR)
            .error(error)
            .logID(logID)
            .build();
    }

    private static WorkflowEvent parseWorkflowEventDone(Integer id, String logID) {
        return WorkflowEvent.builder()
            .id(id)
            .event(WorkflowEventType.DONE)
            .logID(logID)
            .build();
    }

    public static WorkflowEvent parseEvent(Map<String, String> eventLine, String logID) {
        Integer id = Integer.parseInt(eventLine.get("id"));
        WorkflowEventType event = WorkflowEventType.fromString(eventLine.get("event"));
        String data = eventLine.get("data");

        if (WorkflowEventType.MESSAGE.equals(event)) {
            return parseWorkflowEventMessage(id, data, logID);
        } else if (WorkflowEventType.INTERRUPT.equals(event)) {
            return parseWorkflowEventInterrupt(id, data, logID);
        } else if (WorkflowEventType.ERROR.equals(event)) {
            return parseWorkflowEventError(id, data, logID);
        } else if (WorkflowEventType.DONE.equals(event)) {
            return parseWorkflowEventDone(id, logID);
        }
        return parseWorkflowEventMessage(id, data, logID);
    }

    public boolean isDone() {
        return WorkflowEventType.DONE.equals(this.event);
    }
} 
