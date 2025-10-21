package com.coze.openapi.client.workflows;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowGetReq extends BaseReq {

  /** 工作流 ID。 */
  @NonNull
  @JsonProperty("workflow_id")
  private String workflowId;

  /** 是否在返回结果中返回输入和输出参数的结构体。 true：返回输入输出参数结构体 false：不返回输入输出参数结构体 默认值为 false。 */
  @NonNull
  @JsonProperty("include_input_output")
  private Boolean includeInputOutput;
}
