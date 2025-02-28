package com.coze.openapi.service.service.websocket.chat;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;

@AllArgsConstructor
public class WebsocketChatClientBuilder {
  private final String baseUrl;
  private final OkHttpClient httpClient;

  public WebsocketChatClient create(WsChatReq req) {
    return new WebsocketChatClient(httpClient, baseUrl, req);
  }
}
