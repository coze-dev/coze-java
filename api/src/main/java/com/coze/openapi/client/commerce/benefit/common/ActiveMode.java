package com.coze.openapi.client.commerce.benefit.common;

import com.fasterxml.jackson.annotation.JsonValue;

public class ActiveMode {
  public static final ActiveMode ACTIVE_MODE_ABSOLUTE_TIME = new ActiveMode("absolute_time");

  private final String value;

  private ActiveMode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
