package com.coze.openapi.client.websocket.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OutputAudio {
  @JsonProperty("codec")
  private String codec;

  @JsonProperty("pcm_config")
  private PCMConfig pcmConfig;

  @JsonProperty("opus_config")
  private OpusConfig opusConfig;

  @JsonProperty("speech_rate")
  private Integer speechRate;

  @JsonProperty("voice_id")
  private String voiceId;
}
