package com.coze.openapi.service.service.websocket.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public abstract class BaseWebsocketClient {
  protected final ObjectMapper objectMapper = Utils.getMapper();
  protected final WebSocket ws;
  protected final ExecutorService executorService;
  protected static final int CLOSE_TIMEOUT_SECONDS = 10;
  protected final BaseWebSocketListener listener;

  protected BaseWebsocketClient(OkHttpClient client, String url, BaseCallbackHandler handler) {
    Request request = new Request.Builder().url(url).build();
    this.executorService = Executors.newSingleThreadExecutor();
    this.listener = new BaseWebSocketListener(this::handleEvent, handler, this);
    this.ws =
        client.newWebSocket(request, this.listener);
  }

  protected void sendEvent(BaseEvent event) {
    this.ws.send(Utils.toJson(event));
  }

  protected abstract void handleEvent(WebSocket ws, String text);

  public void close() {
    try {
      // 先关闭 WebSocket 连接
      this.ws.close(1000, "Normal closure");

      // 停止接收新任务并等待现有任务完成
      executorService.shutdown();

      // 等待一段时间让任务完成
      if (!executorService.awaitTermination(CLOSE_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
        // 如果超时，强制关闭
        executorService.shutdownNow();
        // 再次等待，让任务响应中断
        if (!executorService.awaitTermination(CLOSE_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
          System.err.println("ExecutorService did not terminate");
        }
      }
    } catch (InterruptedException e) {
      // 恢复中断状态
      Thread.currentThread().interrupt();
      // 强制关闭
      executorService.shutdownNow();
    }finally {
      this.listener.shutdown();
    }
  }
}
