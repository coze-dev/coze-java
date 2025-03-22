package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotWorkflowInfo {
  /**
   * 工作流的唯一标识。
   *
   * <p>示例：7460949086110357***
   */
  @JsonProperty("id")
  private String id;

  /**
   * 工作流名称。
   *
   * <p>示例：示例工作流
   */
  @JsonProperty("name")
  private String name;

  /**
   * 工作流的头像地址。
   *
   * <p>示例：https://*****
   */
  @JsonProperty("icon_url")
  private String iconUrl;

  /**
   * 工作流的描述。
   *
   * <p>示例：工作流的描述。
   */
  @JsonProperty("description")
  private String description;
}
