/* (C)2024 */
package com.coze.openapi.client.bots;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.client.bots.model.BotKnowledge;
import com.coze.openapi.client.bots.model.BotOnboardingInfo;
import com.coze.openapi.client.bots.model.BotPromptInfo;
import com.coze.openapi.client.common.BaseReq;
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
@EqualsAndHashCode(callSuper = true)
public class UpdateBotReq extends BaseReq {

  @NotNull
  @JsonProperty("bot_id")
  private String botID;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("icon_file_id")
  private String iconFileID;

  @JsonProperty("prompt_info")
  private BotPromptInfo promptInfo;

  @JsonProperty("onboarding_info")
  private BotOnboardingInfo onboardingInfo;

  @JsonProperty("knowledge")
  private BotKnowledge knowledge;
}
