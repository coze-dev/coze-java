package com.coze.openapi.service.service.websocket.audio.speech;

import com.coze.openapi.client.common.BaseReq;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WebsocketsAudioSpeechCreateReq extends BaseReq {
  @NonNull private WebsocketsAudioSpeechCallbackHandler callbackHandler;
}
