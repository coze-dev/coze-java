package com.coze.openapi.service.service.websocket.audio.speech;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;

@AllArgsConstructor
public class WebsocketAudioSpeechBuilder {
  private final String baseUrl;
  private final OkHttpClient httpClient;

  public WebsocketAudioSpeechClient create(WebsocketAudioSpeechCreateReq req) {
    return new WebsocketAudioSpeechClient(httpClient, baseUrl, req);
  }
}
