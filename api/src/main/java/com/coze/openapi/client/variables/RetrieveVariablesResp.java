package com.coze.openapi.client.variables;

import java.util.List;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.variables.model.VariableEntity;
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
public class RetrieveVariablesResp extends BaseResp {
  @JsonProperty("items")
  private List<VariableEntity> items;
}
