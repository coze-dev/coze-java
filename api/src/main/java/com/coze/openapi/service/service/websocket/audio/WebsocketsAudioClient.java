package com.coze.openapi.service.service.websocket.audio;

import com.coze.openapi.service.service.websocket.audio.speech.WebsocketsAudioSpeechBuilder;
import com.coze.openapi.service.service.websocket.audio.transcriptions.WebsocketsAudioTranscriptionsBuilder;

import okhttp3.OkHttpClient;

public class WebsocketsAudioClient {

  private final WebsocketsAudioSpeechBuilder websocketAudioSpeechBuilder;
  private final WebsocketsAudioTranscriptionsBuilder websocketAudioTranscriptionsBuilder;

  public WebsocketsAudioClient(String baseUrl, OkHttpClient httpClient) {
    this.websocketAudioSpeechBuilder = new WebsocketsAudioSpeechBuilder(baseUrl, httpClient);
    this.websocketAudioTranscriptionsBuilder =
        new WebsocketsAudioTranscriptionsBuilder(baseUrl, httpClient);
  }

  public WebsocketsAudioSpeechBuilder speech() {
    return websocketAudioSpeechBuilder;
  }

  public WebsocketsAudioTranscriptionsBuilder transcriptions() {
    return websocketAudioTranscriptionsBuilder;
  }
}
