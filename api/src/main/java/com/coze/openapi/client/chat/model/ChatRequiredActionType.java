/* (C)2024 */
package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class ChatRequiredActionType {
  public static final ChatRequiredActionType SUBMIT_TOOL_OUTPUTS =
      new ChatRequiredActionType("submit_tool_outputs");

  private final String value;

  private ChatRequiredActionType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static ChatRequiredActionType fromString(String value) {
    if (SUBMIT_TOOL_OUTPUTS.value.equals(value)) {
      return SUBMIT_TOOL_OUTPUTS;
    }
    return new ChatRequiredActionType(value);
  }
}
