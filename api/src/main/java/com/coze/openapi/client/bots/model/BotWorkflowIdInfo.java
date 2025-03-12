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
public class BotWorkflowIdInfo {
  /** 智能体绑定的工作流 ID */
  @NotNull
  @JsonProperty("id")
  private String id;
}
