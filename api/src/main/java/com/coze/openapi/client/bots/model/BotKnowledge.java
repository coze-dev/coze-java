/* (C)2024 */
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
public class BotKnowledge {
  @JsonProperty("dataset_ids")
  private List<String> datasetIDs;

  @JsonProperty("auto_call")
  private boolean autoCall;

  @JsonProperty("search_strategy")
  private int searchStrategy;
}
