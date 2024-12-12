/* (C)2024 */
package com.coze.openapi.client.connversations.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageRole {
  UNKNOWN("unknown"),
  // Indicates that the content of the message is sent by the user.
  USER("user"),

  // Indicates that the content of the message is sent by the bot.
  ASSISTANT("assistant");

  private final String value;

  MessageRole(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator // 反序列化时使用这个方法
  public static MessageRole fromString(String value) {
    for (MessageRole role : MessageRole.values()) {
      if (role.value.equals(value)) {
        return role;
      }
    }
    return UNKNOWN;
  }
}
