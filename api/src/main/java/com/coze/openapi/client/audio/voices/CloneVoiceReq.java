package com.coze.openapi.client.audio.voices;

import com.coze.openapi.client.audio.common.AudioFormat;
import com.coze.openapi.client.audio.common.LanguageCode;
import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CloneVoiceReq extends BaseReq {
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
  private String voiceID;

  @JsonProperty("preview_text")
  private String previewText;

  private String text;
}
