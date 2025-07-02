package com.coze.openapi.client.variables;

import java.util.List;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.variables.model.VariableValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateVariableReq extends BaseReq {
  @JsonProperty("app_id")
  private String appID;

  @JsonProperty("bot_id")
  private String botID;

  @JsonProperty("connector_id")
  @Builder.Default
  private String connectorID = "1024";

  @NonNull
  @JsonProperty("connector_uid")
  private String connectorUID;

  @NonNull
  @JsonProperty("data")
  private List<VariableValue> data;
}
