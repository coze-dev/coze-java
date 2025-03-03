package com.coze.openapi.client.bots;

import com.coze.openapi.client.bots.model.BotOnboardingInfo;
import com.coze.openapi.client.bots.model.BotPromptInfo;
import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateBotReq extends BaseReq {
  @NonNull
  @JsonProperty("space_id")
  String spaceID;

  @NonNull
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
}
