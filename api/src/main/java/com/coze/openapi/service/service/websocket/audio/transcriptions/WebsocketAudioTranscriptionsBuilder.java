package com.coze.openapi.service.service.websocket.audio.transcriptions;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;

@AllArgsConstructor
public class WebsocketAudioTranscriptionsBuilder {
  private final String baseUrl;
  private final OkHttpClient httpClient;

  public WebsocketAudioTranscriptionsClient create(WebsocketAudioTranscriptionsCreateReq req) {
    return new WebsocketAudioTranscriptionsClient(httpClient, baseUrl, req);
  }
}
