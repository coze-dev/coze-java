/* (C)2024 */
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
public class BotPluginInfo {
  @JsonProperty("plugin_id")
  private String pluginID;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("icon_url")
  private String iconURL;

  @JsonProperty("api_info_list")
  private List<BotPluginAPIInfo> apiInfoList;
}
