package com.coze.openapi.client.workflows;

import com.coze.openapi.client.workflows.model.WorkflowDetail;
import com.coze.openapi.client.workflows.model.WorkflowInput;
import com.coze.openapi.client.workflows.model.WorkflowOutput;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkflowGetResp {

  /** 工作流开始节点的输入参数的结构体。 */
  @JsonProperty("input")
  private WorkflowInput input;
  /** 工作流结束节点的输出参数的结构体。 */
  @JsonProperty("output")
  private WorkflowOutput output;
  /** 工作流的详细信息。 */
  @JsonProperty("workflow_detail")
  private WorkflowDetail workflowDetail;
}
