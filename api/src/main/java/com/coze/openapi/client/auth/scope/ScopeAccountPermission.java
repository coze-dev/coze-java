package com.coze.openapi.client.auth.scope;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScopeAccountPermission {
  @JsonProperty("permission_list")
  private List<String> permissionList;
}
