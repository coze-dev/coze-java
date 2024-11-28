package com.coze.openapi.client.workflows.run.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowEventInterruptData {
    /**
     * The workflow interruption event ID, which should be passed back when resuming the workflow.
     */
    @JsonProperty("event_id")
    private String eventID;

    /**
     * The type of workflow interruption, which should be passed back when resuming the workflow.
     */
    @JsonProperty("type")
    private int type;
}
