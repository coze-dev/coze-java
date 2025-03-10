package com.coze.openapi.service.service.websocket.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.service.service.common.CozeLoggerFactory;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public abstract class BaseWebsocketsClient {
  protected final ObjectMapper objectMapper = Utils.getMapper();
  protected static final Logger logger = CozeLoggerFactory.getLogger();
  protected final WebSocket ws;
  protected final ExecutorService executorService;
  protected static final int CLOSE_TIMEOUT_SECONDS = 10;
  protected final BaseWebsocketsListener listener;

  protected BaseWebsocketsClient(
      OkHttpClient client, String url, BaseCallbackHandler handler, BaseReq req) {
    Request request = new Request.Builder().url(url).build();
    this.executorService = Executors.newSingleThreadExecutor();
    this.listener = new BaseWebsocketsListener(this::handleEvent, handler, this);
    OkHttpClient.Builder builder = client.newBuilder();
    boolean hasSetTimeout = false;
    if (req != null) {
      if (req.getConnectTimeout() != null) {
        hasSetTimeout = true;
        builder.connectTimeout(req.getConnectTimeout(), TimeUnit.SECONDS);
      }
      if (req.getReadTimeout() != null) {
        hasSetTimeout = true;
        builder.readTimeout(req.getReadTimeout(), TimeUnit.SECONDS);
      }
      if (req.getWriteTimeout() != null) {
        hasSetTimeout = true;
        builder.writeTimeout(req.getWriteTimeout(), TimeUnit.SECONDS);
      }
    }
    if (hasSetTimeout) {
      this.ws = builder.build().newWebSocket(request, this.listener);
    } else {
      this.ws = client.newWebSocket(request, this.listener);
    }
  }

  protected void sendEvent(BaseEvent event) {
    this.ws.send(Utils.toJson(event));
  }

  protected abstract void handleEvent(WebSocket ws, String text);

  protected abstract BaseCallbackHandler getCallbackHandler();

  protected String parseEventType(JsonNode jsonNode, String text) {
    JsonNode eventTypeNode = jsonNode.get("event_type");
    if (eventTypeNode == null) {
      logger.error("Missing event_type field in event: {}", text);
      getCallbackHandler()
          .onClientException(this, new RuntimeException("Missing event_type field in event"));
      return null;
    }
    return eventTypeNode.asText();
  }

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
    } finally {
      this.listener.shutdown();
    }
  }
}
