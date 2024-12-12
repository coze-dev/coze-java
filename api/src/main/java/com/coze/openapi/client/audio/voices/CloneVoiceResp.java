package com.coze.openapi.client.audio.voices;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CloneVoiceResp {
    @JsonProperty("voice_id")
    private String voiceID;
}
