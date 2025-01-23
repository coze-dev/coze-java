package com.coze.openapi.client.workspace.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class WorkspaceRoleType {
  public static final WorkspaceRoleType OWNER = new WorkspaceRoleType("owner");
  public static final WorkspaceRoleType ADMIN = new WorkspaceRoleType("admin");
  public static final WorkspaceRoleType MEMBER = new WorkspaceRoleType("member");

  @JsonValue private final String value;

  private WorkspaceRoleType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static WorkspaceRoleType fromString(String value) {
    if ("owner".equals(value)) {
      return OWNER;
    } else if ("admin".equals(value)) {
      return ADMIN;
    } else if ("member".equals(value)) {
      return MEMBER;
    }
    return new WorkspaceRoleType(value);
  }
}
