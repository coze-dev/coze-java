package com.coze.openapi.client.audio.voices;

import java.util.List;

import com.coze.openapi.client.audio.voices.model.Voice;
import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListVoiceResp extends BaseResp {
  @JsonProperty("voice_list")
  private List<Voice> voiceList;
}
