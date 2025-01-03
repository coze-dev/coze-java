package com.coze.openapi.client.connversations.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class MessageRole {
  public static final MessageRole UNKNOWN = new MessageRole("unknown");
  /** Indicates that the content of the message is sent by the user. */
  public static final MessageRole USER = new MessageRole("user");
  /** Indicates that the content of the message is sent by the bot. */
  public static final MessageRole ASSISTANT = new MessageRole("assistant");

  private final String value;

  private MessageRole(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static MessageRole fromString(String value) {
    MessageRole[] roles = {UNKNOWN, USER, ASSISTANT};
    for (MessageRole role : roles) {
      if (role.value.equals(value)) {
        return role;
      }
    }
    return UNKNOWN;
  }
}
