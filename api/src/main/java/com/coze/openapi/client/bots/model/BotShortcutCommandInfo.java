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
public class BotShortcutCommandInfo {
  /**
   * 快捷指令的唯一标识符。
   *
   * <p>示例：745701083525527***
   */
  @JsonProperty("id")
  private String id;

  /**
   * 快捷指令的按钮名称。
   *
   * <p>示例：快捷指令
   */
  @JsonProperty("name")
  private String name;

  /** 快捷指令使用的工具信息。 */
  @JsonProperty("tool")
  private BotShortcutCommandToolInfo tool;

  /**
   * 快捷指令的指令名称。
   *
   * <p>示例：hd_demo
   */
  @JsonProperty("command")
  private String command;

  /**
   * 对对于多Agent类型的智能体，此参数返回快捷指令指定回答的节点 ID。
   *
   * <p>示例：745705134267144****
   */
  @JsonProperty("agent_id")
  private String agentId;

  /**
   * 快捷指令的图标地址。
   *
   * <p>示例：https://*****
   */
  @JsonProperty("icon_url")
  private String iconUrl;

  /** 快捷指令的组件列表。 */
  @JsonProperty("components")
  private List<BotShortcutCommandComponent> components;

  /**
   * 快捷指令的描述。
   *
   * <p>示例：快捷指令示例
   */
  @JsonProperty("description")
  private String description;

  /**
   * 快捷指令的指令内容。
   *
   * <p>示例：搜索今天的新闻讯息
   */
  @JsonProperty("query_template")
  private String queryTemplate;
}
