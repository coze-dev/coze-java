package com.coze.openapi.service.service.websocket.audio.transcriptions;

import com.coze.openapi.client.websocket.event.EventType;
import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.TranscriptionsUpdateEventData;
import com.coze.openapi.client.websocket.event.upstream.*;
import com.coze.openapi.service.service.websocket.common.BaseCallbackHandler;
import com.coze.openapi.service.service.websocket.common.BaseWebsocketClient;
import com.fasterxml.jackson.databind.JsonNode;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class WebsocketAudioTranscriptionsClient extends BaseWebsocketClient {
  private final WebsocketAudioTranscriptionsCallbackHandler handler;
  private static final String uri = "/v1/audio/transcriptions";

  protected WebsocketAudioTranscriptionsClient(
      OkHttpClient client, String wsHost, WebsocketAudioTranscriptionsCreateReq req) {
    super(client, buildUrl(wsHost), req.getCallbackHandler(), req);
    this.handler = req.getCallbackHandler();
  }

  protected static String buildUrl(String wsHost) {
    return String.format("%s%s", wsHost, uri);
  }

  // 发送转录配置更新事件
  public void transcriptionsUpdate(TranscriptionsUpdateEventData data) {
    this.sendEvent(TranscriptionsUpdateEvent.builder().data(data).build());
  }

  // 发送语音缓冲区追加事件
  public void inputAudioBufferAppend(String data) {
    this.sendEvent(InputAudioBufferAppendEvent.of(data));
  }

  public void inputAudioBufferAppend(InputAudioBufferAppendEvent.Data data) {
    this.sendEvent(InputAudioBufferAppendEvent.builder().data(data).build());
  }

  // 发送语音缓冲区清除事件
  public void inputAudioBufferClear() {
    this.sendEvent(new InputAudioBufferClearEvent());
  }

  // 发送语音缓冲区完成事件
  public void inputAudioBufferComplete() {
    this.sendEvent(new InputAudioBufferCompleteEvent());
  }

  @Override
  protected BaseCallbackHandler getCallbackHandler() {
    return handler;
  }

  @Override
  protected void handleEvent(WebSocket ws, String text) {
    try {
      JsonNode jsonNode = objectMapper.readTree(text);
      String eventType = parseEventType(jsonNode, text);
      if (eventType == null) {
        // 这里在上面已经抛出异常了，直接 return 即可
        return;
      }
      switch (eventType) {
        case EventType.TRANSCRIPTIONS_CREATED:
          TranscriptionsCreatedEvent createdEvent =
              objectMapper.treeToValue(jsonNode, TranscriptionsCreatedEvent.class);
          handler.onTranscriptionsCreated(this, createdEvent);
          break;
        case EventType.TRANSCRIPTIONS_UPDATED:
          TranscriptionsUpdatedEvent updatedEvent =
              objectMapper.treeToValue(jsonNode, TranscriptionsUpdatedEvent.class);
          handler.onTranscriptionsUpdated(this, updatedEvent);
          break;
        case EventType.TRANSCRIPTIONS_MESSAGE_UPDATE:
          TranscriptionsMessageUpdateEvent messageUpdateEvent =
              objectMapper.treeToValue(jsonNode, TranscriptionsMessageUpdateEvent.class);
          handler.onTranscriptionsMessageUpdate(this, messageUpdateEvent);
          break;
        case EventType.TRANSCRIPTIONS_MESSAGE_COMPLETED:
          TranscriptionsMessageCompletedEvent messageCompletedEvent =
              objectMapper.treeToValue(jsonNode, TranscriptionsMessageCompletedEvent.class);
          handler.onTranscriptionsMessageCompleted(this, messageCompletedEvent);
          break;
        case EventType.INPUT_AUDIO_BUFFER_CLEARED:
          InputAudioBufferClearedEvent bufferClearedEvent =
              objectMapper.treeToValue(jsonNode, InputAudioBufferClearedEvent.class);
          handler.onInputAudioBufferCleared(this, bufferClearedEvent);
          break;
        case EventType.INPUT_AUDIO_BUFFER_COMPLETED:
          InputAudioBufferCompletedEvent bufferCompletedEvent =
              objectMapper.treeToValue(jsonNode, InputAudioBufferCompletedEvent.class);
          handler.onInputAudioBufferCompleted(this, bufferCompletedEvent);
          break;
        case EventType.ERROR:
          ErrorEvent errorEvent = objectMapper.treeToValue(jsonNode, ErrorEvent.class);
          handler.onError(this, errorEvent);
          break;
        default:
          logger.error("unknown event type: {}, event string: {}", eventType, text);
      }
    } catch (Exception e) {
      handler.onClientException(this, new RuntimeException(e));
    }
  }
}
