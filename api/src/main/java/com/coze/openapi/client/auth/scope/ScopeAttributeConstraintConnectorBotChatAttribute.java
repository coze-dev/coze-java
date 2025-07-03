package com.coze.openapi.client.auth.scope;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ScopeAttributeConstraintConnectorBotChatAttribute {
  @JsonProperty("bot_id_list")
  private List<String> botIDList;
}
