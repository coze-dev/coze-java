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
public class BotSimpleInfo {
  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("icon_url")
  private String iconUrl;

  @JsonProperty("updated_at")
  private Long updatedAt;

  @JsonProperty("description")
  private String description;

  @JsonProperty("is_published")
  private Boolean isPublished;

  @JsonProperty("published_at")
  private Long publishedAt;

  @JsonProperty("owner_user_id")
  private String ownerUserId;
}
