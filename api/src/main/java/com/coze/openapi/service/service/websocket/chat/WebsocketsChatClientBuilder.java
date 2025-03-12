package com.coze.openapi.service.service.websocket.chat;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;

@AllArgsConstructor
public class WebsocketsChatClientBuilder {
  private final String baseUrl;
  private final OkHttpClient httpClient;

  public WebsocketsChatClient create(WebsocketsChatCreateReq req) {
    return new WebsocketsChatClient(httpClient, baseUrl, req);
  }
}
