package com.coze.openapi.client.audio.voices;

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
public class CloneVoiceResp extends BaseResp{
    @JsonProperty("voice_id")
    private String voiceID;
}
