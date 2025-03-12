package com.coze.openapi.service.service.websocket.audio.transcriptions;

import com.coze.openapi.client.common.BaseReq;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WebsocketsAudioTranscriptionsCreateReq extends BaseReq {
  @NonNull private WebsocketsAudioTranscriptionsCallbackHandler callbackHandler;
}
