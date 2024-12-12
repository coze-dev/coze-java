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
public class WorkflowRunHistory {
    /**
     * The ID of execute.
     */
    @JsonProperty("execute_id")
    private String executeId;

    /**
     * Execute status:
     *   success: Execution succeeded.
     *   running: Execution in progress.
     *   fail: Execution failed.
     */
    @JsonProperty("execute_status")
    private WorkflowExecuteStatus executeStatus;

    /**
     * The Bot ID specified when executing the workflow. Returns 0 if no Bot ID was specified.
     */
    @JsonProperty("bot_id")
    private String botId;

    /**
     * The release connector ID of the agent. By default, only the Agent as API connector is
     * displayed, and the connector ID is 1024.
     */
    @JsonProperty("connector_id")
    private String connectorId;

    /**
     * User ID, the user_id specified by the ext field when executing the workflow. If not
     * specified, the token applicant's button ID is returned.
     */
    @JsonProperty("connector_uid")
    private String connectorUid;

    /**
     * How the workflow runs:
     *   0: Synchronous operation.
     *   1: Streaming operation.
     *   2: Asynchronous operation.
     */
    @JsonProperty("run_mode")
    private WorkflowRunMode runMode;

    /**
     * The Log ID of the asynchronously running workflow. If the workflow is executed abnormally,
     * you can contact the service team to troubleshoot the problem through the Log ID.
     */
    @JsonProperty("logid")
    private String logid;

    /**
     * The start time of the workflow, in Unix time timestamp format, in seconds.
     */
    @JsonProperty("create_time")
    private int createTime;

    /**
     * The workflow resume running time, in Unix time timestamp format, in seconds.
     */
    @JsonProperty("update_time")
    private int updateTime;

    /**
     * The output of the workflow is usually a JSON serialized string, but it may also be a
     * non-JSON structured string.
     */
    @JsonProperty("output")
    private String output;

    /**
     * Status code. 0 represents a successful API call. Other values indicate that the call has failed. You can
     * determine the detailed reason for the error through the error_message field.
     */
    @JsonProperty("error_code")
    private int errorCode;

    /**
     * Status message. You can get detailed error information when the API call fails.
     */
    @JsonProperty("error_message")
    private String errorMessage;

    /**
     * Workflow trial run debugging page. Visit this page to view the running results, input
     * and output information of each workflow node.
     */
    @JsonProperty("debug_url")
    private String debugUrl;
} 