package com.coze.openapi.client.connectors;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InstallConnectorReq extends BaseReq {
  @JsonIgnore @NonNull private String connectorID;

  @NonNull
  @JsonProperty("workspace_id")
  private String workspaceID;
}
