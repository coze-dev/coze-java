package com.coze.openapi.client.audio.voices;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListVoiceReq {
    @JsonProperty("filter_system_voice")
    private Boolean filterSystemVoice;

    @JsonProperty("page_num")
    @Builder.Default
    private Integer pageNum = 1;

    @JsonProperty("page_size")
    @Builder.Default
    private Integer pageSize = 100;
}
