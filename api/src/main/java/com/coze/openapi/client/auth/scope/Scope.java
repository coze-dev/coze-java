/* (C)2024 */
package com.coze.openapi.client.auth.scope;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Scope {
  private ScopeAccountPermission accountPermission;
  private ScopeAttributeConstraint attributeConstraint;

  public Map<String, Object> toMap() {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.convertValue(this, new TypeReference<Map<String, Object>>() {});
  }

  public static Scope buildBotChat(List<String> botIDList, List<String> permissionList) {
    if (permissionList == null || permissionList.isEmpty()) {
      permissionList = Collections.singletonList("Connector.botChat");
    }

    ScopeAttributeConstraint attributeConstraint = null;
    if (botIDList != null && !botIDList.isEmpty()) {
      ScopeAttributeConstraintConnectorBotChatAttribute chatAttribute =
          new ScopeAttributeConstraintConnectorBotChatAttribute(botIDList);
      attributeConstraint = new ScopeAttributeConstraint(chatAttribute);
    }

    return new Scope(new ScopeAccountPermission(permissionList), attributeConstraint);
  }
}
