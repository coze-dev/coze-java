package com.coze.openapi.client.audio.voices;

import java.util.List;

import com.coze.openapi.client.audio.voices.model.Voice;
import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListVoiceResp extends BaseResp{
    @JsonProperty("voice_list")
    private List<Voice> voiceList;
}
