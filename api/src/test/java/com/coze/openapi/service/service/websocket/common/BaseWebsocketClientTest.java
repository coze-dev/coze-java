package com.coze.openapi.service.service.websocket.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.client.websocket.common.BaseEvent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

class BaseWebsocketClientTest {

  @Mock private OkHttpClient mockOkHttpClient;
  @Mock private WebSocket mockWebSocket;
  @Mock private BaseCallbackHandler mockCallbackHandler;
  @Mock private BaseWebSocketListener mockListener;

  private TestBaseWebsocketClient client;

  // 创建一个测试用的具体实现类
  private static class TestBaseWebsocketClient extends BaseWebsocketClient {
    public TestBaseWebsocketClient(OkHttpClient client, String url, BaseCallbackHandler handler) {
      super(client, url, handler, null);
    }

    @Override
    protected void handleEvent(WebSocket ws, String text) {
      // 测试实现
    }

    // 暴露 protected 方法用于测试
    public void testSendEvent(BaseEvent event) {
      sendEvent(event);
    }
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(mockOkHttpClient.newWebSocket(any(Request.class), any(BaseWebSocketListener.class)))
        .thenReturn(mockWebSocket);

    client = new TestBaseWebsocketClient(mockOkHttpClient, "ws://test.com", mockCallbackHandler);
  }

  @Test
  void testInitialization() {
    verify(mockOkHttpClient).newWebSocket(any(Request.class), any(BaseWebSocketListener.class));
  }

  @Test
  void testSendEvent() {
    // 创建测试事件
    BaseEvent testEvent = new BaseEvent();

    // 发送事件
    client.testSendEvent(testEvent);

    // 验证 WebSocket.send() 被调用
    verify(mockWebSocket).send(anyString());
  }

  @Test
  void testClose() throws InterruptedException {
    // 执行关闭
    client.close();

    // 验证 WebSocket 关闭被调用
    verify(mockWebSocket).close(eq(1000), eq("Normal closure"));

    // 等待一小段时间确保关闭操作完成
    TimeUnit.MILLISECONDS.sleep(100);

    // 再次调用关闭，确保不会出错
    client.close();
  }

  @Test
  void testCloseWithInterruption() throws InterruptedException {
    // 创建一个会导致中断的模拟
    doAnswer(
            invocation -> {
              Thread.currentThread().interrupt();
              return null;
            })
        .when(mockWebSocket)
        .close(anyInt(), anyString());

    // 执行关闭
    client.close();

    // 验证 WebSocket 关闭被调用
    verify(mockWebSocket).close(eq(1000), eq("Normal closure"));

    // 清除中断状态
    Thread.interrupted();
  }

  @Test
  void testExecutorServiceShutdown() throws InterruptedException {
    // 发送多个事件以测试 ExecutorService
    BaseEvent testEvent = new BaseEvent();
    for (int i = 0; i < 5; i++) {
      client.testSendEvent(testEvent);
    }

    // 关闭客户端
    client.close();

    // 验证所有消息都被发送
    verify(mockWebSocket, times(5)).send(anyString());
  }

  @Test
  void testListenerShutdown() {
    // 使用反射设置模拟的 listener
    try {
      java.lang.reflect.Field listenerField =
          BaseWebsocketClient.class.getDeclaredField("listener");
      listenerField.setAccessible(true);
      listenerField.set(client, mockListener);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // 关闭客户端
    client.close();

    // 验证 listener 的 shutdown 被调用
    verify(mockListener).shutdown();
  }

  @Test
  void testHandleEventDelegation() {
    String testMessage = "test message";

    // 调用 handleEvent
    client.handleEvent(mockWebSocket, testMessage);

    // 由于是空实现，这里主要验证调用不会抛出异常
  }
}
