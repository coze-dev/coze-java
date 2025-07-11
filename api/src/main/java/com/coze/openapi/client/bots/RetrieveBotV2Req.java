package com.coze.openapi.client.bots;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetrieveBotV2Req extends BaseReq {
  @JsonProperty("bot_id")
  private String botID;

  @JsonProperty("is_published")
  private Boolean isPublished;

  public static RetrieveBotV2Req of(String botID, Boolean isPublished) {
    return RetrieveBotV2Req.builder().botID(botID).isPublished(isPublished).build();
  }
}
