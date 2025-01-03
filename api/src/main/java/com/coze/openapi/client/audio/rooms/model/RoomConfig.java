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
}
