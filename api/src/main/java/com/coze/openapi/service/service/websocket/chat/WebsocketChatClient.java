package com.coze.openapi.service.service.websocket.chat;

import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.websocket.event.EventType;
import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.ChatUpdateEventData;
import com.coze.openapi.client.websocket.event.upstream.*;
import com.coze.openapi.client.websocket.event.upstream.ChatUpdateEvent;
import com.coze.openapi.client.websocket.event.upstream.ConversationChatCancelEvent;
import com.coze.openapi.client.websocket.event.upstream.ConversationChatSubmitToolOutputsEvent;
import com.coze.openapi.service.service.websocket.common.BaseWebsocketClient;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class WebsocketChatClient extends BaseWebsocketClient {

  private final ObjectMapper objectMapper = Utils.getMapper();

  private final WebsocketChatCallbackHandler handler;

  private static final String uri = "/v1/chat";

  protected WebsocketChatClient(OkHttpClient client, String wsHost, WebsocketChatCreateReq req) {
    super(client, buildUrl(wsHost, req), req.getCallbackHandler(), req);
    this.handler = req.getCallbackHandler();
  }

  protected static String buildUrl(String wsHost, WebsocketChatCreateReq req) {
    return String.format("%s%s?bot_id=%s", wsHost, uri, req.getBotID());
  }

  public void chatUpdate(ChatUpdateEventData data) {
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
    this.sendEvent(InputAudioBufferAppendEvent.of(data));
  }

  public void inputAudioBufferClear() {
    this.sendEvent(new InputAudioBufferClearEvent());
  }

  public void inputAudioBufferComplete() {
    this.sendEvent(new InputAudioBufferCompleteEvent());
  }

  @Override
  protected void handleEvent(WebSocket ws, String text) {
    try {
      // 解析 JSON
      JsonNode jsonNode = objectMapper.readTree(text);
      String eventType = jsonNode.get("event_type").asText();

      switch (eventType) {
        case EventType.CHAT_CREATED:
          ChatCreatedEvent chatCreatedEvent =
              objectMapper.treeToValue(jsonNode, ChatCreatedEvent.class);
          handler.onChatCreated(this, chatCreatedEvent);
          break;
        case EventType.CHAT_UPDATED:
          ChatUpdatedEvent chatUpdatedEvent =
              objectMapper.treeToValue(jsonNode, ChatUpdatedEvent.class);
          handler.onChatUpdated(this, chatUpdatedEvent);
          break;
        case EventType.CONVERSATION_AUDIO_COMPLETED:
          ConversationAudioCompletedEvent audioCompletedEvent =
              objectMapper.treeToValue(jsonNode, ConversationAudioCompletedEvent.class);
          handler.onConversationAudioCompleted(this, audioCompletedEvent);
          break;
        case EventType.CONVERSATION_AUDIO_DELTA:
          ConversationAudioDeltaEvent audioDeltaEvent =
              objectMapper.treeToValue(jsonNode, ConversationAudioDeltaEvent.class);
          handler.onConversationAudioDelta(this, audioDeltaEvent);
          break;
        case EventType.CONVERSATION_AUDIO_TRANSCRIPT_COMPLETED:
          ConversationAudioTranscriptCompletedEvent transcriptCompletedEvent =
              objectMapper.treeToValue(jsonNode, ConversationAudioTranscriptCompletedEvent.class);
          handler.onConversationAudioTranscriptCompleted(this, transcriptCompletedEvent);
          break;
        case EventType.CONVERSATION_AUDIO_TRANSCRIPT_UPDATE:
          ConversationAudioTranscriptUpdateEvent transcriptUpdateEvent =
              objectMapper.treeToValue(jsonNode, ConversationAudioTranscriptUpdateEvent.class);
          handler.onConversationAudioTranscriptUpdate(this, transcriptUpdateEvent);
          break;
        case EventType.CONVERSATION_CHAT_CANCELED:
          ConversationChatCanceledEvent chatCanceledEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatCanceledEvent.class);
          handler.onConversationChatCanceled(this, chatCanceledEvent);
          break;
        case EventType.CONVERSATION_CHAT_COMPLETED:
          ConversationChatCompletedEvent chatCompletedEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatCompletedEvent.class);
          handler.onConversationChatCompleted(this, chatCompletedEvent);
          break;
        case EventType.CONVERSATION_CHAT_CREATED:
          ConversationChatCreatedEvent conversationChatCreatedEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatCreatedEvent.class);
          handler.onConversationChatCreated(this, conversationChatCreatedEvent);
          break;
        case EventType.CONVERSATION_CHAT_FAILED:
          ConversationChatFailedEvent chatFailedEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatFailedEvent.class);
          handler.onConversationChatFailed(this, chatFailedEvent);
          break;
        case EventType.CONVERSATION_CHAT_IN_PROGRESS:
          ConversationChatInProgressEvent chatInProgressEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatInProgressEvent.class);
          handler.onConversationChatInProgress(this, chatInProgressEvent);
          break;
        case EventType.CONVERSATION_CHAT_REQUIRES_ACTION:
          ConversationChatRequiresActionEvent chatRequiresActionEvent =
              objectMapper.treeToValue(jsonNode, ConversationChatRequiresActionEvent.class);
          handler.onConversationChatRequiresAction(this, chatRequiresActionEvent);
          break;
        case EventType.CONVERSATION_CLEARED:
          ConversationClearedEvent clearedEvent =
              objectMapper.treeToValue(jsonNode, ConversationClearedEvent.class);
          handler.onConversationCleared(this, clearedEvent);
          break;
        case EventType.CONVERSATION_MESSAGE_COMPLETED:
          ConversationMessageCompletedEvent messageCompletedEvent =
              objectMapper.treeToValue(jsonNode, ConversationMessageCompletedEvent.class);
          handler.onConversationMessageCompleted(this, messageCompletedEvent);
          break;
        case EventType.CONVERSATION_MESSAGE_DELTA:
          ConversationMessageDeltaEvent messageDeltaEvent =
              objectMapper.treeToValue(jsonNode, ConversationMessageDeltaEvent.class);
          handler.onConversationMessageDelta(this, messageDeltaEvent);
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
