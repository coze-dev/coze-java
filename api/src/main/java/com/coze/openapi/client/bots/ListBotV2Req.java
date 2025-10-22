package com.coze.openapi.client.bots;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListBotV2Req extends BaseReq {
  @JsonProperty("workspace_id")
  private String workspaceID;
  @JsonProperty("publish_status")
  private String publishStatus;
  @JsonProperty("connector_id")
  private String connectorID;
  @JsonProperty("page_num")
  private Integer pageNum;
  @JsonProperty("page_size")
  private Integer pageSize;
}
