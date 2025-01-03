package com.coze.openapi.client.connversations.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/** The content type of the multimodal message. */
@Getter
public class MessageObjectStringType {
  public static final MessageObjectStringType UNKNOWN = new MessageObjectStringType("unknown");
  public static final MessageObjectStringType TEXT = new MessageObjectStringType("text");
  public static final MessageObjectStringType FILE = new MessageObjectStringType("file");
  public static final MessageObjectStringType IMAGE = new MessageObjectStringType("image");
  public static final MessageObjectStringType AUDIO = new MessageObjectStringType("audio");

  private final String value;

  private MessageObjectStringType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static MessageObjectStringType fromString(String value) {
    MessageObjectStringType[] types = {UNKNOWN, TEXT, FILE, IMAGE, AUDIO};
    for (MessageObjectStringType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return UNKNOWN;
  }
}
