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

  @JsonProperty("knowledge")
  private BotCommonKnowledge knowledge;

  @JsonProperty("variables")
  private List<BotVariable> variables;

  @JsonProperty("owner_user_id")
  private String ownerUserId;

  @JsonProperty("voice_info_list")
  private List<BotVoice> voiceInfoList;

  @JsonProperty("shortcut_commands")
  private List<BotShortcutCommandInfo> shortcutCommands;

  @JsonProperty("workflow_info_list")
  private List<BotWorkflowInfo> workflowInfoList;

  @JsonProperty("background_image_info")
  private BotBackgroundImageInfo backgroundImageInfo;

  @JsonProperty("default_user_input_type")
  private String defaultUserInputType;
}
