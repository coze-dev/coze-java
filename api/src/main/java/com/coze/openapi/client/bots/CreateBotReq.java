package com.coze.openapi.client.bots;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.client.bots.model.BotOnboardingInfo;
import com.coze.openapi.client.bots.model.BotPromptInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBotReq {
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
}
