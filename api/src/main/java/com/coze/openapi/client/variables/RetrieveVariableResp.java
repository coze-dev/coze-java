package com.coze.openapi.client.variables;

import java.util.List;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.variables.model.VariableValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RetrieveVariableResp extends BaseResp {
  @JsonProperty("items")
  private List<VariableValue> items;
}
