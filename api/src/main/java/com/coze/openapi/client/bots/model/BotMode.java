package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class BotMode {
  /** Single agent mode */
  public static final BotMode SINGLE_AGENT = new BotMode(0);
  /** Multi agent mode */
  public static final BotMode MULTI_AGENT = new BotMode(1);
  /** Single agent workflow mode */
  public static final BotMode SINGLE_AGENT_WORKFLOW = new BotMode(2);

  @JsonValue
  private final Integer value;

  private BotMode(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static BotMode fromValue(Integer value) {
    BotMode[] modes = {SINGLE_AGENT, MULTI_AGENT, SINGLE_AGENT_WORKFLOW};
    for (BotMode mode : modes) {
      if (mode.value.equals(value)) {
        return mode;
      }
    }
    return new BotMode(value);
  }
}
