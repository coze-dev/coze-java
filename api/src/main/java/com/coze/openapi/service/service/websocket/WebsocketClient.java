package com.coze.openapi.service.service.websocket;

import com.coze.openapi.service.service.websocket.chat.WebsocketChatClientBuilder;

import okhttp3.OkHttpClient;

public class WebsocketClient {

  private final WebsocketChatClientBuilder chat;

  public WebsocketClient(OkHttpClient client, String baseURL) {
    this.chat = new WebsocketChatClientBuilder(baseURL, client);
  }

  public WebsocketChatClientBuilder chat() {
    return chat;
  }
}
