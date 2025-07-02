package com.coze.openapi.client.variables;

import java.util.List;

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
public class RetrieveVariableReq extends BaseReq {
  @JsonProperty("app_id")
  private String appID;

  @JsonProperty("bot_id")
  private String botID;

  @JsonProperty("connector_id")
  @Builder.Default
  private String connectorID = "1024";

  @JsonProperty("connector_uid")
  private String connectorUID;

  @JsonIgnore private List<String> keywords;
}
