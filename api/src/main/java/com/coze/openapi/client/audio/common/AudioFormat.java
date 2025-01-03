package com.coze.openapi.client.audio.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class AudioFormat {
  public static final AudioFormat WAV = new AudioFormat("wav");
  public static final AudioFormat PCM = new AudioFormat("pcm");
  public static final AudioFormat OGG_OPUS = new AudioFormat("ogg_opus");
  public static final AudioFormat M4A = new AudioFormat("m4a");
  public static final AudioFormat AAC = new AudioFormat("aac");
  public static final AudioFormat MP3 = new AudioFormat("mp3");

  private final String value;

  private AudioFormat(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static AudioFormat fromString(String value) {
    AudioFormat[] formats = {WAV, PCM, OGG_OPUS, M4A, AAC, MP3};
    for (AudioFormat format : formats) {
      if (format.value.equals(value)) {
        return format;
      }
    }
    return new AudioFormat(value);
  }
}
