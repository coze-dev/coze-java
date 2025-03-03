package com.coze.openapi.service.service.websocket.chat;

import com.coze.openapi.client.websocket.event.downstream.ChatCreatedEvent;
import com.coze.openapi.client.websocket.event.downstream.ChatUpdatedEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationAudioCompletedEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationAudioDeltaEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationAudioTranscriptCompletedEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationAudioTranscriptUpdateEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationChatCanceledEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationChatCompletedEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationChatCreatedEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationChatFailedEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationChatInProgressEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationChatRequiresActionEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationClearedEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationMessageCompletedEvent;
import com.coze.openapi.client.websocket.event.downstream.ConversationMessageDeltaEvent;
import com.coze.openapi.client.websocket.event.downstream.InputAudioBufferClearedEvent;
import com.coze.openapi.client.websocket.event.downstream.InputAudioBufferCompletedEvent;
import com.coze.openapi.service.service.websocket.common.BaseCallbackHandler;

public abstract class WebsocketChatCallbackHandler
    extends BaseCallbackHandler<WebsocketChatClient> {
  public WebsocketChatCallbackHandler() {}

  // 对话连接成功事件 (chat.created)
  public void onChatCreated(WebsocketChatClient client, ChatCreatedEvent event) {}

  // 对话配置成功事件 (chat.updated)
  public void onChatUpdated(WebsocketChatClient client, ChatUpdatedEvent event) {}

  // 对话创建事件 (conversation.chat.created)
  public void onConversationChatCreated(
      WebsocketChatClient client, ConversationChatCreatedEvent event) {}

  // 对话正在处理事件 (conversation.chat.in_progress)
  public void onConversationChatInProgress(
      WebsocketChatClient client, ConversationChatInProgressEvent event) {}

  // 增量消息事件 (conversation.message.delta)
  public void onConversationMessageDelta(
      WebsocketChatClient client, ConversationMessageDeltaEvent event) {}

  // 增量语音事件 (conversation.audio.delta)
  public void onConversationAudioDelta(
      WebsocketChatClient client, ConversationAudioDeltaEvent event) {}

  // 消息完成事件 (conversation.message.completed)
  public void onConversationMessageCompleted(
      WebsocketChatClient client, ConversationMessageCompletedEvent event) {}

  // 语音回复完成事件 (conversation.audio.completed)
  public void onConversationAudioCompleted(
      WebsocketChatClient client, ConversationAudioCompletedEvent event) {}

  // 对话完成事件 (conversation.chat.completed)
  public void onConversationChatCompleted(
      WebsocketChatClient client, ConversationChatCompletedEvent event) {}

  // 对话失败事件 (conversation.chat.failed)
  public void onConversationChatFailed(
      WebsocketChatClient client, ConversationChatFailedEvent event) {}

  // 语音提交成功事件 (input_audio_buffer.completed)
  public void onInputAudioBufferCompleted(
      WebsocketChatClient client, InputAudioBufferCompletedEvent event) {}

  // 语音清除成功事件 (input_audio_buffer.cleared)
  public void onInputAudioBufferCleared(
      WebsocketChatClient client, InputAudioBufferClearedEvent event) {}

  // 对话清除事件 (conversation.cleared)
  public void onConversationCleared(WebsocketChatClient client, ConversationClearedEvent event) {}

  // 对话取消事件 (conversation.chat.canceled)
  public void onConversationChatCanceled(
      WebsocketChatClient client, ConversationChatCanceledEvent event) {}

  // 语音转录更新事件 (conversation.audio_transcript.update)
  public void onConversationAudioTranscriptUpdate(
      WebsocketChatClient client, ConversationAudioTranscriptUpdateEvent event) {}

  // 语音转录完成事件 (conversation.audio_transcript.completed)
  public void onConversationAudioTranscriptCompleted(
      WebsocketChatClient client, ConversationAudioTranscriptCompletedEvent event) {}

  // 端插件事件 (conversation.chat.requires_action)
  public void onConversationChatRequiresAction(
      WebsocketChatClient client, ConversationChatRequiresActionEvent event) {}
}
