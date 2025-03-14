package com.coze.openapi.client.auth.scope;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScopeAttributeConstraint {

  @JsonProperty("connector_bot_chat_attribute")
  private ScopeAttributeConstraintConnectorBotChatAttribute connectorBotChatAttribute;
}
