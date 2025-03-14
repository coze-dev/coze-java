package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class BotShortcutCommandToolType {
  /** Workflow tool type */
  public static final BotShortcutCommandToolType WORKFLOW =
      new BotShortcutCommandToolType("workflow");
  /** Plugin tool type */
  public static final BotShortcutCommandToolType PLUGIN = new BotShortcutCommandToolType("plugin");

  @JsonValue private final String value;

  private BotShortcutCommandToolType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static BotShortcutCommandToolType fromValue(String value) {
    BotShortcutCommandToolType[] types = {WORKFLOW, PLUGIN};
    for (BotShortcutCommandToolType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return new BotShortcutCommandToolType(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
