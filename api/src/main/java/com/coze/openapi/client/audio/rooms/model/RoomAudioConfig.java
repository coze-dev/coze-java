package com.coze.openapi.client.audio.rooms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomAudioConfig {
  @Builder.Default private AudioCodec codec = AudioCodec.OPUS;
}
