package com.coze.openapi.client.audio.rooms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomConfig {
  @JsonProperty("audio_config")
  private RoomAudioConfig audioConfig;

  public static RoomConfig of(AudioCodec codec) {
    return RoomConfig.builder().audioConfig(RoomAudioConfig.builder().codec(codec).build()).build();
  }

  private String room_mode = "";

  private TranslateConfig translate_config;

  class TranslateConfig {
    private String from = "";
    private String to = "";

    public String getFrom() {
      return from;
    }

    public void setFrom(String from) {
      this.from = from;
    }

    public String getTo() {
      return to;
    }

    public void setTo(String to) {
      this.to = to;
    }
  }

  public String getRoom_mode() {
    return room_mode;
  }

  public void setRoom_mode(String room_mode) {
    this.room_mode = room_mode;
  }

  public TranslateConfig getTranslate_config() {
    return translate_config;
  }

  public void setTranslate_config(TranslateConfig translate_config) {
    this.translate_config = translate_config;
  }
}
