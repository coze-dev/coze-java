package com.coze.openapi.service.service.websocket.audio.transcriptions;

import com.coze.openapi.client.websocket.event.downstream.InputAudioBufferClearedEvent;
import com.coze.openapi.client.websocket.event.downstream.InputAudioBufferCompletedEvent;
import com.coze.openapi.client.websocket.event.downstream.TranscriptionsCreatedEvent;
import com.coze.openapi.client.websocket.event.downstream.TranscriptionsMessageCompletedEvent;
import com.coze.openapi.client.websocket.event.downstream.TranscriptionsMessageUpdateEvent;
import com.coze.openapi.client.websocket.event.downstream.TranscriptionsUpdatedEvent;
import com.coze.openapi.service.service.websocket.common.BaseCallbackHandler;

public abstract class WebsocketsAudioTranscriptionsCallbackHandler
    extends BaseCallbackHandler<WebsocketsAudioTranscriptionsClient> {
  public WebsocketsAudioTranscriptionsCallbackHandler() {}

  // 转录创建事件 (transcriptions.created)
  public void onTranscriptionsCreated(
      WebsocketsAudioTranscriptionsClient client, TranscriptionsCreatedEvent event) {}

  // 转录配置更新事件 (transcriptions.updated)
  public void onTranscriptionsUpdated(
      WebsocketsAudioTranscriptionsClient client, TranscriptionsUpdatedEvent event) {}

  // 转录消息更新事件 (transcriptions.message.update)
  public void onTranscriptionsMessageUpdate(
      WebsocketsAudioTranscriptionsClient client, TranscriptionsMessageUpdateEvent event) {}

  // 转录消息完成事件 (transcriptions.message.completed)
  public void onTranscriptionsMessageCompleted(
      WebsocketsAudioTranscriptionsClient client, TranscriptionsMessageCompletedEvent event) {}

  // 语音缓冲区清除事件 (input_audio_buffer.cleared)
  public void onInputAudioBufferCleared(
      WebsocketsAudioTranscriptionsClient client, InputAudioBufferClearedEvent event) {}

  // 语音缓冲区完成事件 (input_audio_buffer.completed)
  public void onInputAudioBufferCompleted(
      WebsocketsAudioTranscriptionsClient client, InputAudioBufferCompletedEvent event) {}
}
