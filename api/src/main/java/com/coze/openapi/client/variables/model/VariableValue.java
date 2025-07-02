package com.coze.openapi.client.variables.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariableValue {
  @JsonProperty("value")
  private String value; // 用户变量的值

  @JsonProperty("keyword")
  private String keyword; // 用户变量的名称

  @JsonProperty("create_time")
  private Long createTime;

  @JsonProperty("update_time")
  private Long updateTime;
}
