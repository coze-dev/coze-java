/* (C)2024 */
package com.coze.openapi.client.audio.voices;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListVoiceReq extends BaseReq {
  @JsonProperty("filter_system_voice")
  private Boolean filterSystemVoice;

  @JsonProperty("page_num")
  @Builder.Default
  private Integer pageNum = 1;

  @JsonProperty("page_size")
  @Builder.Default
  private Integer pageSize = 100;
}
