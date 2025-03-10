package com.coze.openapi.service.service.websocket;

import com.coze.openapi.service.service.websocket.audio.WebsocketsAudioClient;
import com.coze.openapi.service.service.websocket.chat.WebsocketsChatClientBuilder;

import okhttp3.OkHttpClient;

public class WebsocketsClient {

  private final WebsocketsChatClientBuilder chat;

  private final WebsocketsAudioClient audio;

  public WebsocketsClient(OkHttpClient client, String baseURL) {
    this.chat = new WebsocketsChatClientBuilder(baseURL, client);
    this.audio = new WebsocketsAudioClient(baseURL, client);
  }

  public WebsocketsChatClientBuilder chat() {
    return chat;
  }

  public WebsocketsAudioClient audio() {
    return audio;
  }
}
