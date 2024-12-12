/* (C)2024 */
package com.coze.openapi.client.connversations.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
  @JsonProperty("id")
  private String id;

  @JsonProperty("created_at")
  private Integer createdAt;

  @JsonProperty("meta_data")
  private Map<String, String> metaData;

  /*
   * section_id is used to distinguish the context sections of the session history.
   * The same section is one context.
   * */
  @JsonProperty("last_section_id")
  private String lastSectionID;
}
