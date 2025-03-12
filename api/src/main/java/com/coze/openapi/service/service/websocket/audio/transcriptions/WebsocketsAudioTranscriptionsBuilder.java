package com.coze.openapi.service.service.websocket.audio.transcriptions;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;

@AllArgsConstructor
public class WebsocketsAudioTranscriptionsBuilder {
  private final String baseUrl;
  private final OkHttpClient httpClient;

  public WebsocketsAudioTranscriptionsClient create(WebsocketsAudioTranscriptionsCreateReq req) {
    return new WebsocketsAudioTranscriptionsClient(httpClient, baseUrl, req);
  }
}
