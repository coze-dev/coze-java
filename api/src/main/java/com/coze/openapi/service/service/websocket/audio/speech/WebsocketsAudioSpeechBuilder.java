package com.coze.openapi.service.service.websocket.audio.speech;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;

@AllArgsConstructor
public class WebsocketsAudioSpeechBuilder {
  private final String baseUrl;
  private final OkHttpClient httpClient;

  public WebsocketsAudioSpeechClient create(WebsocketsAudioSpeechCreateReq req) {
    return new WebsocketsAudioSpeechClient(httpClient, baseUrl, req);
  }
}
