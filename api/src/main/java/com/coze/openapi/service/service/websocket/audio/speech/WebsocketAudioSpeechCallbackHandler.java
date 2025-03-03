package com.coze.openapi.service.service.websocket.audio.speech;

import com.coze.openapi.client.websocket.event.downstream.InputTextBufferCompletedEvent;
import com.coze.openapi.client.websocket.event.downstream.SpeechAudioCompletedEvent;
import com.coze.openapi.client.websocket.event.downstream.SpeechAudioUpdateEvent;
import com.coze.openapi.client.websocket.event.downstream.SpeechCreatedEvent;
import com.coze.openapi.client.websocket.event.downstream.SpeechUpdatedEvent;
import com.coze.openapi.service.service.websocket.common.BaseCallbackHandler;

public abstract class WebsocketAudioSpeechCallbackHandler
    extends BaseCallbackHandler<WebsocketAudioSpeechClient> {
  public WebsocketAudioSpeechCallbackHandler() {}

  // 语音创建成功事件 (speech.created)
  public void onSpeechCreated(WebsocketAudioSpeechClient client, SpeechCreatedEvent event) {}

  // 语音配置更新事件 (speech.update)
  public void onSpeechUpdated(WebsocketAudioSpeechClient client, SpeechUpdatedEvent event) {}

  // 语音数据更新事件 (speech.audio.update)
  public void onSpeechAudioUpdate(
      WebsocketAudioSpeechClient client, SpeechAudioUpdateEvent event) {}

  // 语音数据完成事件 (speech.audio.completed)
  public void onSpeechAudioCompleted(
      WebsocketAudioSpeechClient client, SpeechAudioCompletedEvent event) {}

  // 文本缓冲区完成事件 (input_text_buffer.completed)
  public void onInputTextBufferCompleted(
      WebsocketAudioSpeechClient client, InputTextBufferCompletedEvent event) {}
}
