package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bot {
    @JsonProperty("bot_id")
    private String botID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("icon_url")
    private String iconURL;

    @JsonProperty("create_time")
    private Long createTime;

    @JsonProperty("update_time")
    private Long updateTime;

    @JsonProperty("version")
    private String version;

    @JsonProperty("prompt_info")
    private BotPromptInfo promptInfo;

    @JsonProperty("onboarding_info")
    private BotOnboardingInfo onboardingInfo;

    @JsonProperty("bot_mode")
    private BotMode botMode;

    @JsonProperty("plugin_info_list")
    private List<BotPluginInfo> pluginInfoList;

    @JsonProperty("model_info")
    private BotModelInfo modelInfo;
} 