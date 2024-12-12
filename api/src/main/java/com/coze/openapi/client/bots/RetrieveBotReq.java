/* (C)2024 */
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
public class RetrieveBotReq extends BaseReq {
  @JsonProperty("bot_id")
  private String botID;

  public static RetrieveBotReq of(String botID) {
    return RetrieveBotReq.builder().botID(botID).build();
  }
}
