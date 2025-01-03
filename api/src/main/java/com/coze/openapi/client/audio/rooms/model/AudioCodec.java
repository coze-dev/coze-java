package com.coze.openapi.client.audio.rooms.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class AudioCodec {
  public static final AudioCodec AACLC = new AudioCodec("AACLC");
  public static final AudioCodec G711A = new AudioCodec("G711A");
  public static final AudioCodec OPUS = new AudioCodec("OPUS");
  public static final AudioCodec G722 = new AudioCodec("G722");

  private final String value;

  private AudioCodec(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static AudioCodec fromString(String value) {
    AudioCodec[] codecs = {AACLC, G711A, OPUS, G722};
    for (AudioCodec codec : codecs) {
      if (codec.value.equals(value)) {
        return codec;
      }
    }
    return new AudioCodec(value);
  }
}
