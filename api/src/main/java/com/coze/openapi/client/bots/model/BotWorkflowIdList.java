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
public class BotWorkflowIdList {
  /** 智能体的工作流列表配置 */
  @JsonProperty("ids")
  private List<BotWorkflowIdInfo> ids;
}
