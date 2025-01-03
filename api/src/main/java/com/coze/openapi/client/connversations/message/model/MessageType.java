package com.coze.openapi.client.connversations.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class MessageType {
  /** User input content. */
  public static final MessageType QUESTION = new MessageType("question");
  /** The message content returned by the Bot to the user, supporting incremental return. */
  public static final MessageType ANSWER = new MessageType("answer");
  /** Intermediate results of the function (function call) called during the Bot conversation process. */
  public static final MessageType FUNCTION_CALL = new MessageType("function_call");
  /** Results returned after calling the tool (function call). */
  public static final MessageType TOOL_OUTPUT = new MessageType("tool_output");
  /** Results returned after calling the tool (function call). */
  public static final MessageType TOOL_RESPONSE = new MessageType("tool_response");
  /** If the user question suggestion switch is turned on in the Bot configuration, the reply content related to the recommended questions will be returned. */
  public static final MessageType FOLLOW_UP = new MessageType("follow_up");
  /** In the scenario of multiple answers, the server will return a verbose package. */
  public static final MessageType VERBOSE = new MessageType("verbose");
  public static final MessageType UNKNOWN = new MessageType("");

  private final String value;

  private MessageType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static MessageType fromString(String value) {
    MessageType[] types = {QUESTION, ANSWER, FUNCTION_CALL, TOOL_OUTPUT, TOOL_RESPONSE, FOLLOW_UP, VERBOSE, UNKNOWN};
    for (MessageType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return UNKNOWN;
  }
}
