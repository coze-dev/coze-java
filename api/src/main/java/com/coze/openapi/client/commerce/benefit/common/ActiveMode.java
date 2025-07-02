package com.coze.openapi.client.commerce.benefit.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ActiveMode {
  public static final ActiveMode ACTIVE_MODE_ABSOLUTE_TIME = new ActiveMode("absolute_time");

  private final String value;

  private ActiveMode(String value) {
    if (value == null) {
           throw new IllegalArgumentException("ActiveMode value cannot be null");
    }
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
