package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotModelInfo {

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
  @JsonProperty("model_id")
  private String modelID;

  /**
   * 最大回复长度。
   *
   * <p>示例：4096
   */
  @JsonProperty("max_tokens")
  private Integer maxTokens;

  /**
   * 模型名称
   *
   * <p>示例：豆包·Function call模型
   */
  @JsonProperty("model_name")
  private String modelName;

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

  /** 输出格式。 */
  @JsonProperty("response_format")
  private BotResponseFormat responseFormat;

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
