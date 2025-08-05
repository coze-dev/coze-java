package com.coze.openapi.client.workflows.run;

import com.coze.openapi.client.chat.model.ChatUsage;
import com.coze.openapi.client.common.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RunWorkflowResp extends BaseResponse<String> {
  /*
  Execution ID of asynchronous execution. Only returned when the workflow is executed
  asynchronously (is_async=true). You can use execute_id to call the Query Workflow
  Asynchronous Execution Result API to obtain the final execution result of the workflow.
  * */
  @JsonProperty("execute_id")
  private String executeID;

  /*
  Workflow execution result, usually a JSON serialized string. In some scenarios, a
  string with a non-JSON structure may be returned.
  * */
  @JsonProperty("data")
  private String data;

  @JsonProperty("debug_url")
  private String debugURL;

  @JsonProperty("token")
  private Integer token;

  @JsonProperty("cost")
  private String cost;

  /** Token usage information for the workflow execution. */
  @JsonProperty("usage")
  private ChatUsage usage;
}
