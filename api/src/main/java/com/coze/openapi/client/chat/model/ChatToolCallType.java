package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class ChatToolCallType {
  public static final ChatToolCallType FUNCTION = new ChatToolCallType("function");
  public static final ChatToolCallType REPLY_MESSAGE = new ChatToolCallType("reply_message");

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
    if (REPLY_MESSAGE.value.equals(value)) {
      return REPLY_MESSAGE;
    }
    return new ChatToolCallType(value);
  }
}
