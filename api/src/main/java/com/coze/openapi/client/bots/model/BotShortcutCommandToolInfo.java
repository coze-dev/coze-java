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
public class BotShortcutCommandToolInfo {
  /** 头条新闻 */
  @JsonProperty("name")
  private String name;

  /** 工具类型 */
  @JsonProperty("type")
  private BotShortcutCommandToolType type;
}
