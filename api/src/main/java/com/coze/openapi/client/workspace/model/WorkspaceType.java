package com.coze.openapi.client.workspace.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class WorkspaceType {
  public static final WorkspaceType PERSONAL = new WorkspaceType("personal");
  public static final WorkspaceType TEAM = new WorkspaceType("team");

  @JsonValue private final String value;

  private WorkspaceType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static WorkspaceType fromString(String value) {
    if ("personal".equals(value)) {
      return PERSONAL;
    } else if ("team".equals(value)) {
      return TEAM;
    }
    return new WorkspaceType(value);
  }
}
