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
public class WorkflowParameter {

  /** 该参数的类型。 */
  @JsonProperty("type")
  private String type;
  /** 当参数类型为 array 时，该字段用于定义数组元素的子类型。 */
  @JsonProperty("items")
  private WorkflowParameter items;
  /** 标识输入参数是否为必填项。 true：该参数为必填项。 false：该参数为可选项。 */
  @JsonProperty("required")
  private Boolean required;
  /** 当参数类型为 object 时，该字段用于定义数组元素的子类型。 */
  @JsonProperty("properties")
  private Map<String, WorkflowParameter> properties;
  /** 该参数的描述信息。 */
  @JsonProperty("description")
  private String description;
  /** 该参数配置的默认值。 */
  @JsonProperty("default_value")
  private String defaultValue;
}
