package com.coze.openapi.client.connversations.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageType {
  // User input content.
  QUESTION("question"),

  // The message content returned by the Bot to the user, supporting incremental return.
  ANSWER("answer"),

  // Intermediate results of the function (function call) called during the Bot conversation
  // process.
  FUNCTION_CALL("function_call"),

  // Results returned after calling the tool (function call).
  TOOL_OUTPUT("tool_output"),

  // Results returned after calling the tool (function call).
  TOOL_RESPONSE("tool_response"),

  // If the user question suggestion switch is turned on in the Bot configuration, the reply content
  // related to the recommended questions will be returned.
  FOLLOW_UP("follow_up"),

  // In the scenario of multiple answers, the server will return a verbose package.
  VERBOSE("verbose"),

  UNKNOWN("");

  private final String value;

  MessageType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator // 反序列化时使用这个方法
  public static MessageType fromString(String value) {
    for (MessageType type : MessageType.values()) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return UNKNOWN;
  }
}
