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
public class BotModelInfoConfig {
  /**
   * Top K。
   *
   * <p>示例：1
   */
  @JsonProperty("top_k")
  private Integer topK;

  /**
   * Top P，即累计概率。
   *
   * <p>示例：1.0
   */
  @JsonProperty("top_p")
  private Double topP;

  /**
   * 智能体绑定的模型 ID。
   *
   * <p>示例：1706077826
   */
  @NotNull
  @JsonProperty("model_id")
  private String modelId;

  /**
   * 最大回复长度。
   *
   * <p>示例：4096
   */
  @JsonProperty("max_tokens")
  private Integer maxTokens;

  /**
   * 生成随机性。
   *
   * <p>示例：1.0
   */
  @JsonProperty("temperature")
  private Double temperature;

  /**
   * 携带上下文轮数。
   *
   * <p>示例：30
   */
  @JsonProperty("context_round")
  private Integer contextRound;

  /**
   * 输出格式。取值：
   *
   * <ul>
   *   <li>text：文本。
   *   <li>markdown：Markdown格式。
   *   <li>json：JSON格式。
   * </ul>
   *
   * 示例：text
   */
  @JsonProperty("response_format")
  private String responseFormat;

  /**
   * 重复主题惩罚。
   *
   * <p>示例：0.0
   */
  @JsonProperty("presence_penalty")
  private Double presencePenalty;

  /**
   * 重复语句惩罚。
   *
   * <p>示例：0.0
   */
  @JsonProperty("frequency_penalty")
  private Double frequencyPenalty;
}
