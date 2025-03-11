package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotPluginIdList {
  /**
   * 智能体的插件列表配置
   */
  @JsonProperty("id_list")
  private List<BotPluginIdInfo> idList;
}