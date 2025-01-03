package com.coze.openapi.client.connversations.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class MessageContentType {
  public static final MessageContentType UNKNOWN = new MessageContentType("unknown");
  /** Text. */
  public static final MessageContentType TEXT = new MessageContentType("text");
  /** Multimodal content, that is, a combination of text and files, or a combination of text and images. */
  public static final MessageContentType OBJECT_STRING = new MessageContentType("object_string");
  /** Message card. This type only appears in the interface response and is not supported as an input parameter. */
  public static final MessageContentType CARD = new MessageContentType("card");
  /** If there is a voice message in the input message, the conversation.audio.delta event will be returned in the streaming response event. */
  public static final MessageContentType AUDIO = new MessageContentType("audio");

  private final String value;

  private MessageContentType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static MessageContentType fromString(String value) {
    MessageContentType[] types = {UNKNOWN, TEXT, OBJECT_STRING, CARD, AUDIO};
    for (MessageContentType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return UNKNOWN;
  }
}
