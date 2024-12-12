package com.coze.openapi.client.audio.speech;

import com.coze.openapi.client.audio.common.AudioFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSpeechReq {
    @NonNull
    @JsonProperty("input")
    private String input;
    @NonNull
    @JsonProperty("voice_id")
    private String voiceId;
    @Builder.Default
    @JsonProperty("response_format")
    private AudioFormat responseFormat = AudioFormat.MP3;
    @JsonProperty("speed")
    @Builder.Default    
    private float speed = 1.0f;
}
