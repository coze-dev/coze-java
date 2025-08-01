package com.coze.openapi.client.bots;

import java.util.List;

import com.coze.openapi.client.bots.model.BotSimpleInfo;
import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class ListBotV2Resp extends BaseResp {
  @JsonProperty("items")
  private List<BotSimpleInfo> items;

  @JsonProperty("total")
  private Integer total;
}
