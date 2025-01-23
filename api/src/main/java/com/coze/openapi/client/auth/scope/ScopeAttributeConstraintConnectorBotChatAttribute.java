package com.coze.openapi.client.auth.scope;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ScopeAttributeConstraintConnectorBotChatAttribute {
  private List<String> botIDList;
}
