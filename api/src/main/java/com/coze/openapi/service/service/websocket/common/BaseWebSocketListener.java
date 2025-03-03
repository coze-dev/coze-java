package com.coze.openapi.service.service.websocket.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class BaseWebSocketListener extends WebSocketListener {
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private final BiConsumer<WebSocket, String> handleEvent;
  private final BaseCallbackHandler callbackHandler;
  private final BaseWebsocketClient client;

  public BaseWebSocketListener(
      BiConsumer<WebSocket, String> handleEvent,
      BaseCallbackHandler handler,
      BaseWebsocketClient client) {
    this.handleEvent = handleEvent;
    this.callbackHandler = handler;
    this.client = client;
  }

  @Override
  public void onOpen(WebSocket webSocket, okhttp3.Response response) {
    System.out.println("onOpen");
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    executorService.submit(() -> this.handleEvent.accept(webSocket, text));
  }

  @Override
  public void onMessage(WebSocket webSocket, ByteString bytes) {
    executorService.submit(() -> this.handleEvent.accept(webSocket, bytes.utf8()));
  }

  @Override
  public void onClosing(WebSocket webSocket, int code, String reason) {
    this.callbackHandler.onClosing(this.client, code, reason);
  }

  @Override
  public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
    this.callbackHandler.onFailure(
        this.client, new RuntimeException("websocket connection error: " + t.getMessage(), t));
  }

  @Override
  public void onClosed(WebSocket webSocket, int code, String reason) {
    this.callbackHandler.onClosed(this.client, code, reason);
  }

  public void shutdown() {
    if (!executorService.isShutdown()) {
      executorService.shutdown();
    }
  }
}
