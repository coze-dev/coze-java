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
public class BotShortcutCommandComponent {
  /**
   * 组件的名称。
   *
   * <p>示例：query
   */
  @JsonProperty("name")
  private String name;

  /** 组件的类型 */
  @JsonProperty("type")
  private BotShortcutCommandComponentType type;

  /**
   * 组件是否隐藏。
   *
   * <p>示例：false
   */
  @JsonProperty("is_hide")
  private Boolean isHide;

  /**
   * 组件的选项列表或支持的文件类型。
   *
   * <p>当 {@code type} 为 {@code select} 时，{@code options} 为组件的选项列表。
   *
   * <p>当 {@code type} 为 {@code file} 时，{@code options} 为组件支持的文件类型，取值为：
   *
   * <ul>
   *   <li>{@code image}: 图片；
   *   <li>{@code doc}: 文档；
   *   <li>{@code table}: 表格；
   *   <li>{@code audio}: 音频；
   *   <li>{@code video}: 视频；
   *   <li>{@code zip}: 压缩包；
   *   <li>{@code code}: 代码；
   *   <li>{@code txt}: TXT；
   *   <li>{@code ppt}: PPT。
   * </ul>
   */
  @JsonProperty("options")
  private List<String> options;

  /**
   * 组件的描述。
   *
   * <p>示例：新闻搜索关键词
   */
  @JsonProperty("description")
  private String description;

  /**
   * 组件的默认值。
   *
   * <p>示例：科技新闻
   */
  @JsonProperty("default_value")
  private String defaultValue;

  /**
   * 组件对应的工具的参数名称。
   *
   * <p>示例：query
   */
  @JsonProperty("tool_parameter")
  private String toolParameter;
}
