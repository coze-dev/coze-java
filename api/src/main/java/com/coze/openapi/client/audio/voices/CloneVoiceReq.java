package com.coze.openapi.client.audio.voices;

import com.coze.openapi.client.audio.common.AudioFormat;
import com.coze.openapi.client.audio.common.LanguageCode;
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
public class CloneVoiceReq {
    @NonNull
    @JsonProperty("voice_name")
    private String voiceName;
    @NonNull
    @JsonProperty("file_path")
    private String filePath;
    @NonNull
    @JsonProperty("audio_format")
    private AudioFormat audioFormat;
    @JsonProperty("language")
    private LanguageCode language;
    @JsonProperty("voice_id")
    private String voiceId;
    @JsonProperty("preview_text")
    private String previewText;
    private String text;
}
