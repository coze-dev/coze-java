/* (C)2024 */
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
public class BotPluginAPIInfo {
  @JsonProperty("api_id")
  private String apiID;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;
}
