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

  @JsonProperty("room_mode")
  private String room_mode = "";

  @JsonProperty("translate_config")
  private TranslateConfig translate_config;

  @Data
  @NoArgsConstructor
  public static class TranslateConfig {
    @JsonProperty("from")
    private String from = "";
    
    @JsonProperty("to")
    private String to = "";
  }
}
