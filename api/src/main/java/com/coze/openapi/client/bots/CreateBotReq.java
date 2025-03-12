package com.coze.openapi.client.bots;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.client.bots.model.*;
import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateBotReq extends BaseReq {
  @NotNull
  @JsonProperty("space_id")
  String spaceID;

  @NotNull
  @JsonProperty("name")
  String name;

  @JsonProperty("description")
  String description;

  @JsonProperty("icon_file_id")
  String iconFileID;

  @JsonProperty("prompt_info")
  BotPromptInfo promptInfo;

  @JsonProperty("onboarding_info")
  BotOnboardingInfo onboardingInfo;

  /** 智能体的插件配置 */
  @JsonProperty("plugin_id_list")
  private BotPluginIdList pluginIdList;

  /** 智能体的工作流配置 */
  @JsonProperty("workflow_id_list")
  private BotWorkflowIdList workflowIdList;

  /** 智能体的模型配置 */
  @JsonProperty("model_info_config")
  private BotModelInfoConfig modelInfoConfig;
}
