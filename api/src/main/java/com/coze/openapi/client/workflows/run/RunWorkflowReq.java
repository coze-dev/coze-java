package com.coze.openapi.client.workflows.run;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunWorkflowReq {
    /*
    * The ID of the workflow, which should have been published.
    * */
    @NonNull
    @JsonProperty("workflow_id")
    private String workflowID;

    /*
     * Input parameters and their values for the starting node of the workflow. You can view the
     * list of parameters on the arrangement page of the specified workflow.
     * */
    private Map<String, Object> parameters;

    /*
     * The associated Bot ID required for some workflow executions,
     * such as workflows with database nodes, variable nodes, etc.
     * */
    @JsonProperty("bot_id")
    private String botID;

    /*
     *  Used to specify some additional fields in the format of Map[String][String]
     * */
    @JsonProperty("ext")
    private Map<String, String> ext;

    /*
     * Whether to run asynchronously.
     * */
    @JsonProperty("is_async")
    private Boolean isAsync;
}
