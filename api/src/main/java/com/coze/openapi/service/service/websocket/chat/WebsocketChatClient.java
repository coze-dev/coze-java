package com.coze.openapi.service.service.websocket.chat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.ChatUpdateEventData;
import com.coze.openapi.client.websocket.event.upstream.*;
import com.coze.openapi.client.websocket.event.upstream.ChatUpdateEvent;
import com.coze.openapi.client.websocket.event.upstream.ConversationChatCancelEvent;
import com.coze.openapi.client.websocket.event.upstream.ConversationChatSubmitToolOutputsEvent;
import com.coze.openapi.service.service.websocket.common.BaseWebSocketListener;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class WebsocketChatClient {

  private final ObjectMapper objectMapper = Utils.getMapper();

  private final WebSocket ws;

  private final WebsocketChatCallbackHandler handler;

  private static final String uri = "/v1/chat";

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  public WebsocketChatClient(OkHttpClient client, String wsHost, WsChatReq req) {
    String url = String.format("%s%s?bot_id=%s", wsHost, uri, req.getBotID());
    Request request = new Request.Builder().url(url).build();
    this.handler = req.getCallbackHandler();
    this.ws = client.newWebSocket(request, new BaseWebSocketListener<>(this::handleEvent, this.handler, this));
  }

  private void sendEvent(BaseEvent event) {
    this.ws.send(Utils.toJson(event));
  }

  private void chatUpdate(ChatUpdateEventData data) {
    this.sendEvent(ChatUpdateEvent.builder().data(data).build());
  }

  public void conversationChatCancel() {
    this.sendEvent(new ConversationChatCancelEvent());
  }

  public void conversationChatSubmitToolOutputs(ConversationChatSubmitToolOutputsEvent.Data data) {
    this.sendEvent(ConversationChatSubmitToolOutputsEvent.builder().data(data).build());
  }

  public void conversationClear() {
    this.sendEvent(new ConversationClearEvent());
  }

  public void conversationMessageCreate(Message data) {
    this.sendEvent(ConversationMessageCreateEvent.builder().data(data).build());
  }

  public void inputAudioBufferAppend(InputAudioBufferAppendEvent.Data data) {
    this.sendEvent(InputAudioBufferAppendEvent.builder().data(data).build());
  }

  public void inputAudioBufferAppend(String data) {
    this.sendEvent(
        InputAudioBufferAppendEvent.builder()
            .data(new InputAudioBufferAppendEvent.Data(data))
            .build());
  }

  public void inputAudioBufferClear() {
    this.sendEvent(new InputAudioBufferClearEvent());
  }

  public void inputAudioBufferComplete() {
    this.sendEvent(new InputAudioBufferCompleteEvent());
  }

  void handleEvent(WebSocket ws, String text) {
    try {
      // 解析 JSON
      JsonNode jsonNode = objectMapper.readTree(text);
      String eventType = jsonNode.get("event_type").asText();

      switch (eventType) {
        case EventType.CHAT_CREATED:
          ChatCreatedEvent chatCreatedEvent =
              objectMapper.treeToValue(jsonNode, ChatCreatedEvent.class);
          handler.onChatCreated(WebsocketChatClient.this, chatCreatedEvent);
          break;
        case EventType.CHAT_UPDATED:
          ChatUpdatedEvent chatUpdatedEvent =
              objectMapper.treeToValue(jsonNode, ChatUpdatedEvent.class);
          handler.onChatUpdated(WebsocketChatClient.this, chatUpdatedEvent);
          break;
        case EventType.CONVERSATION_AUDIO_COMPLETED:
          ConversationAudioCompletedEvent audioCompletedEvent =
              objectMapper.treeToValue(jsonNode, ConversationAudioCompletedEvent.class);
          handler.onConversationAudioCompleted(WebsocketChatClient.this, audioCompletedEvent);
          break;
        case EventType.CONVERSATION_AUDIO_DELTA:
          ConversationAudioDeltaEvent audioDeltaEvent =
              objectMapper.treeToValue(jsonNode, ConversationAudioDeltaEvent.class);
          handler.onConversationAudioDelta(WebsocketChatClient.this, audioDeltaEvent);
          break;
        case EventType.CONVERSATION_AUDIO_TRANSCRIPT_COMPLETED:
          ConversationAudioTranscriptCompletedEvent transcriptCompletedEvent =
              objectMapper.treeToValue(jsonNode, ConversationAudioTranscriptCompletedEvent.class);
          handler.onConversationAudioTranscriptCompleted(
              WebsocketChatClient.this, transcriptCompletedEvent);
          break;
        case EventType.CONVERSATION_AUDIO_TRANSCRIPT_UPDATE:
          ConversationAudioTranscriptUpdateEvent transcriptUpdateEvent =
              objectMapper.treeToValue(jsonNode, ConversationAudioTranscriptUpdateEvent.class);
          handler.onConversationAudioTranscriptUpdate(
              WebsocketChatClient.this, transcriptUpdateEvent);
          break;
        case EventType.CONVERSATION_CHAT_CANCELED:
          ConversationChatCanceledEvent chatCanceledEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatCanceledEvent.class);
          handler.onConversationChatCanceled(WebsocketChatClient.this, chatCanceledEvent);
          break;
        case EventType.CONVERSATION_CHAT_COMPLETED:
          ConversationChatCompletedEvent chatCompletedEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatCompletedEvent.class);
          handler.onConversationChatCompleted(WebsocketChatClient.this, chatCompletedEvent);
          break;
        case EventType.CONVERSATION_CHAT_CREATED:
          ConversationChatCreatedEvent conversationChatCreatedEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatCreatedEvent.class);
          handler.onConversationChatCreated(WebsocketChatClient.this, conversationChatCreatedEvent);
          break;
        case EventType.CONVERSATION_CHAT_FAILED:
          ConversationChatFailedEvent chatFailedEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatFailedEvent.class);
          handler.onConversationChatFailed(WebsocketChatClient.this, chatFailedEvent);
          break;
        case EventType.CONVERSATION_CHAT_IN_PROGRESS:
          ConversationChatInProgressEvent chatInProgressEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatInProgressEvent.class);
          handler.onConversationChatInProgress(WebsocketChatClient.this, chatInProgressEvent);
          break;
        case EventType.CONVERSATION_CHAT_REQUIRES_ACTION:
          ConversationChatRequiresActionEvent chatRequiresActionEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatRequiresActionEvent.class);
          handler.onConversationChatRequiresAction(
              WebsocketChatClient.this, chatRequiresActionEvent);
          break;
        case EventType.CONVERSATION_CLEARED:
          ConversationClearedEvent clearedEvent =
              objectMapper.treeToValue(jsonNode, ConversationClearedEvent.class);
          handler.onConversationCleared(WebsocketChatClient.this, clearedEvent);
          break;
        case EventType.CONVERSATION_MESSAGE_COMPLETED:
          ConversationMessageCompletedEvent messageCompletedEvent =
              objectMapper.treeToValue(jsonNode, ConversationMessageCompletedEvent.class);
          handler.onConversationMessageCompleted(WebsocketChatClient.this, messageCompletedEvent);
          break;
        case EventType.CONVERSATION_MESSAGE_DELTA:
          ConversationMessageDeltaEvent messageDeltaEvent =
              objectMapper.treeToValue(jsonNode, ConversationMessageDeltaEvent.class);
          handler.onConversationMessageDelta(WebsocketChatClient.this, messageDeltaEvent);
          break;
        case EventType.INPUT_AUDIO_BUFFER_CLEARED:
          InputAudioBufferClearedEvent bufferClearedEvent =
              objectMapper.treeToValue(jsonNode, InputAudioBufferClearedEvent.class);
          handler.onInputAudioBufferCleared(WebsocketChatClient.this, bufferClearedEvent);
          break;
        case EventType.INPUT_AUDIO_BUFFER_COMPLETED:
          InputAudioBufferCompletedEvent bufferCompletedEvent =
              objectMapper.treeToValue(jsonNode, InputAudioBufferCompletedEvent.class);
          handler.onInputAudioBufferCompleted(WebsocketChatClient.this, bufferCompletedEvent);
          break;
        case EventType.ERROR:
          ErrorEvent errorEvent = objectMapper.treeToValue(jsonNode, ErrorEvent.class);
          handler.onError(WebsocketChatClient.this, errorEvent);
          break;
        default:
          // todo 用 log
          System.out.println("未知事件类型: " + eventType);
      }
    } catch (Exception e) {
      handler.onClientException(WebsocketChatClient.this, new RuntimeException(e));
    }
  }

  public void close() {
    this.ws.close(1000, null);
    executorService.shutdown();
  }
}
