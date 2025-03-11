package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotWorkflowIdList {
  /**
   * 智能体的工作流列表配置
   */
  @JsonProperty("ids")
  private List<BotWorkflowIdInfo> ids;
}