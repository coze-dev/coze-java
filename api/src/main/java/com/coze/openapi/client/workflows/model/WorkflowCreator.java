package com.coze.openapi.client.workflows.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowCreator {

  /** 工作流创建者的扣子用户 ID。 */
  @JsonProperty("id")
  private String id;
  /** 工作流创建者的扣子用户名。 */
  @JsonProperty("name")
  private String name;
}
