/* (C)2024 */
package com.coze.openapi.client.workspace.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {
  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("icon_url")
  private String iconUrl;

  @JsonProperty("role_type")
  private WorkspaceRoleType roleType;

  @JsonProperty("workspace_type")
  private WorkspaceType workspaceType;
}
