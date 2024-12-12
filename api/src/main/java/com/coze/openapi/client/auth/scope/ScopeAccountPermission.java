/* (C)2024 */
package com.coze.openapi.client.auth.scope;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScopeAccountPermission {
  private List<String> permissionList;
}
