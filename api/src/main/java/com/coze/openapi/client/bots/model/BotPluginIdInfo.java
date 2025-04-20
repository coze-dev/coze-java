package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotPluginIdInfo {

  /** 智能体绑定的插件工具 ID */
  @NonNull
  @JsonProperty("api_id")
  private String apiId;

  /** 智能体绑定的插件 ID */
  @NonNull
  @JsonProperty("plugin_id")
  private String pluginId;
}
