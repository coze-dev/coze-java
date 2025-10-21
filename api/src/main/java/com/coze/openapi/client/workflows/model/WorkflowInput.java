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
public class WorkflowInput {

  /** 开始节点的输入参数结构体。 */
  @JsonProperty("parameters")
  private Map<String, WorkflowParameter> parameters;
}
