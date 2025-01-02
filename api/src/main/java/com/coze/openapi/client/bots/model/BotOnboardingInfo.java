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
public class BotOnboardingInfo {
  @JsonProperty("prologue")
  private String prologue;

  @JsonProperty("suggested_questions")
  private List<String> suggestedQuestions;
}
