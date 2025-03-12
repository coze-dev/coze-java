package com.coze.openapi.client.bots.model;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotPluginIdInfo {

  /** 智能体绑定的插件工具 ID */
  @NotNull
  @JsonProperty("api_id")
  private String apiId;

  /** 智能体绑定的插件 ID */
  @NotNull
  @JsonProperty("plugin_id")
  private String pluginId;
}
