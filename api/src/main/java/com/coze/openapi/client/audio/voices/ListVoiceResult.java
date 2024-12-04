package com.coze.openapi.client.audio.voices;

import java.util.List;

import com.coze.openapi.client.audio.voices.model.Voice;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListVoiceResult {
    @JsonProperty("voice_list")
    private List<Voice> voiceList;
}
