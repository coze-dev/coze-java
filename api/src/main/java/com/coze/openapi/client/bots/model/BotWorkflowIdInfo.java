package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotWorkflowIdInfo {
  /** 智能体绑定的工作流 ID */
  @NonNull
  @JsonProperty("id")
  private String id;
}
