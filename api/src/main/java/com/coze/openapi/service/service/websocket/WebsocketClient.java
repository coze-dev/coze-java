package com.coze.openapi.service.service.websocket;

import com.coze.openapi.service.service.websocket.audio.WebsocketAudioClient;
import com.coze.openapi.service.service.websocket.chat.WebsocketChatClientBuilder;

import okhttp3.OkHttpClient;

public class WebsocketClient {

  private final WebsocketChatClientBuilder chat;

  private final WebsocketAudioClient audio;

  public WebsocketClient(OkHttpClient client, String baseURL) {
    this.chat = new WebsocketChatClientBuilder(baseURL, client);
    this.audio = new WebsocketAudioClient(baseURL, client);
  }

  public WebsocketChatClientBuilder chat() {
    return chat;
  }

  public WebsocketAudioClient audio() {
    return audio;
  }
}
