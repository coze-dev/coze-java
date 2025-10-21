package com.coze.openapi.client.workflows.model;

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
public class WorkflowOutput {

  /** 工作流结束节点输出变量的数组。以键值对形式存储，格式为 { "变量名称": { "type": "变量类型" } }。 */
  @JsonProperty("parameters")
  private Map<String, WorkflowParameter> parameters;
  /** 工作流结束节点返回文本时，智能体回复内容的结构。仅当 terminate_plan 为 use_answer_content 时会返回。 */
  @JsonProperty("content")
  private String content;
  /** 结束节点的返回类型，枚举值： return_variables：返回变量。 use_answer_content：返回文本。 */
  @JsonProperty("terminate_plan")
  private String terminatePlan;
}
