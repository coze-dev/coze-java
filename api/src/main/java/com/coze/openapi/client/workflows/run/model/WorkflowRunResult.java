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
public class WorkflowRunResult {
    @JsonProperty("debug_url")
    private String debugUrl;

    /**
     * Workflow execution result, usually a JSON serialized string. In some scenarios, a
     * string with a non-JSON structure may be returned.
     */
    @JsonProperty("data")
    private String data;

    /**
     * Execution ID of asynchronous execution. Only returned when the workflow is executed
     * asynchronously (is_async=true). You can use execute_id to call the Query Workflow
     * Asynchronous Execution Result API to obtain the final execution result of the workflow.
     */
    @JsonProperty("execute_id")
    private String executeId;
} 