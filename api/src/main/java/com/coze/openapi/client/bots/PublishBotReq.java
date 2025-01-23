package com.coze.openapi.client.bots;

import java.util.List;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PublishBotReq extends BaseReq {
  @NonNull
  @JsonProperty("bot_id")
  private String botID;

  @JsonProperty("connector_ids")
  private List<String> connectorIDs;
}
