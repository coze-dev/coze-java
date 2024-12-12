/* (C)2024 */
package com.coze.openapi.client.audio.rooms.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AudioCodec {
  AACLC("AACLC"),
  G711A("G711A"),
  OPUS("OPUS"),
  G722("G722");

  private final String value;

  AudioCodec(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static AudioCodec fromString(String value) {
    for (AudioCodec codec : AudioCodec.values()) {
      if (codec.value.equals(value)) {
        return codec;
      }
    }
    throw new IllegalArgumentException("Invalid audio codec: " + value);
  }
}
