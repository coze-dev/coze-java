package com.coze.openapi.client.audio.transcriptions;

import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateTranscriptionsResp extends BaseResp {
  @JsonProperty("text")
  private String text;
}
