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
public class BotPluginInfo {
    @JsonProperty("plugin_id")
    private String pluginID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("icon_url")
    private String iconUrl;

    @JsonProperty("api_info_list")
    private List<BotPluginAPIInfo> apiInfoList;
} 