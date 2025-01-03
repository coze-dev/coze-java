package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class ChatToolCallType {
  public static final ChatToolCallType FUNCTION = new ChatToolCallType("function");

  private final String value;

  private ChatToolCallType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static ChatToolCallType fromString(String value) {
    if (FUNCTION.value.equals(value)) {
      return FUNCTION;
    }
    throw new IllegalArgumentException("Unknown ChatToolCallType: " + value);
  }
}
