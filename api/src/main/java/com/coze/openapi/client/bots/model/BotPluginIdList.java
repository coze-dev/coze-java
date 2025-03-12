package com.coze.openapi.client.bots.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotPluginIdList {
  /** 智能体的插件列表配置 */
  @JsonProperty("id_list")
  private List<BotPluginIdInfo> idList;
}
