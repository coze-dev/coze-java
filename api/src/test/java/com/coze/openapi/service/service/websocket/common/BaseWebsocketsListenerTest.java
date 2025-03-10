package com.coze.openapi.service.service.websocket.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

class BaseWebsocketsListenerTest {

  @Mock private WebSocket mockWebSocket;
  private Response mockResponse;
  @Mock private BaseCallbackHandler mockCallbackHandler;
  @Mock private BaseWebsocketsClient mockClient;
  @Mock private BiConsumer<WebSocket, String> mockHandleEvent;

  private BaseWebsocketsListener listener;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // 使用 Builder 创建真实的 Response 对象
    mockResponse =
        new Response.Builder()
            .code(200)
            .message("OK")
            .protocol(Protocol.HTTP_1_1)
            .request(new Request.Builder().url("ws://test.com").build())
            .build();

    listener = new BaseWebsocketsListener(mockHandleEvent, mockCallbackHandler, mockClient);
  }

  @Test
  void testOnOpen() {
    listener.onOpen(mockWebSocket, mockResponse);
    // onOpen 只是打印日志，不需要验证
  }

  @Test
  void testOnMessage_String() throws InterruptedException {
    String message = "test message";
    CountDownLatch latch = new CountDownLatch(1);

    doAnswer(
            invocation -> {
              latch.countDown();
              return null;
            })
        .when(mockHandleEvent)
        .accept(any(), any());

    listener.onMessage(mockWebSocket, message);

    // 等待异步处理完成
    latch.await(1, TimeUnit.SECONDS);
    verify(mockHandleEvent).accept(mockWebSocket, message);
  }

  @Test
  void testOnMessage_ByteString() throws InterruptedException {
    String message = "test message";
    ByteString byteString = ByteString.encodeUtf8(message);
    CountDownLatch latch = new CountDownLatch(1);

    doAnswer(
            invocation -> {
              latch.countDown();
              return null;
            })
        .when(mockHandleEvent)
        .accept(any(), any());

    listener.onMessage(mockWebSocket, byteString);

    // 等待异步处理完成
    latch.await(1, TimeUnit.SECONDS);
    verify(mockHandleEvent).accept(mockWebSocket, message);
  }

  @Test
  void testOnClosing() {
    int code = 1000;
    String reason = "normal closure";

    listener.onClosing(mockWebSocket, code, reason);

    verify(mockCallbackHandler).onClosing(mockClient, code, reason);
  }

  @Test
  void testOnClosed() {
    int code = 1000;
    String reason = "normal closure";

    listener.onClosed(mockWebSocket, code, reason);

    verify(mockCallbackHandler).onClosed(mockClient, code, reason);
  }

  @Test
  void testOnFailure() {
    Throwable error = new RuntimeException("test error");

    listener.onFailure(mockWebSocket, error, mockResponse);

    verify(mockCallbackHandler).onFailure(eq(mockClient), any(RuntimeException.class));
  }

  @Test
  void testShutdown() {
    listener.shutdown();

    // 再次调用 shutdown 确保不会出错
    listener.shutdown();
  }

  @Test
  void testMultipleMessages() throws InterruptedException {
    int messageCount = 5;
    CountDownLatch latch = new CountDownLatch(messageCount);

    doAnswer(
            invocation -> {
              latch.countDown();
              return null;
            })
        .when(mockHandleEvent)
        .accept(any(), any());

    // 发送多条消息
    for (int i = 0; i < messageCount; i++) {
      String message = "message " + i;
      listener.onMessage(mockWebSocket, message);
    }

    // 等待所有消息处理完成
    latch.await(2, TimeUnit.SECONDS);
    verify(mockHandleEvent, times(messageCount)).accept(any(), any());
  }
}
