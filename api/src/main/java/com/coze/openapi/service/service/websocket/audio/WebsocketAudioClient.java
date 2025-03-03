package com.coze.openapi.service.service.websocket.audio;

import com.coze.openapi.service.service.websocket.audio.speech.WebsocketAudioSpeechBuilder;
import com.coze.openapi.service.service.websocket.audio.transcriptions.WebsocketAudioTranscriptionsBuilder;

import okhttp3.OkHttpClient;

public class WebsocketAudioClient {

  private final WebsocketAudioSpeechBuilder websocketAudioSpeechBuilder;
  private final WebsocketAudioTranscriptionsBuilder websocketAudioTranscriptionsBuilder;

  public WebsocketAudioClient(String baseUrl, OkHttpClient httpClient) {
    this.websocketAudioSpeechBuilder = new WebsocketAudioSpeechBuilder(baseUrl, httpClient);
    this.websocketAudioTranscriptionsBuilder =
        new WebsocketAudioTranscriptionsBuilder(baseUrl, httpClient);
  }

  public WebsocketAudioSpeechBuilder speech() {
    return websocketAudioSpeechBuilder;
  }

  public WebsocketAudioTranscriptionsBuilder transcriptions() {
    return websocketAudioTranscriptionsBuilder;
  }
}
