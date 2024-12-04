package com.coze.openapi.client.audio.voices;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CloneVoiceResult {
    @JsonProperty("voice_id")
    private String voiceID;
}
