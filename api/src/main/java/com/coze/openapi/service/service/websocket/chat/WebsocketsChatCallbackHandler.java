package com.coze.openapi.service.service.websocket.chat;

import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.service.service.websocket.common.BaseCallbackHandler;

public abstract class WebsocketsChatCallbackHandler
    extends BaseCallbackHandler<WebsocketsChatClient> {
  public WebsocketsChatCallbackHandler() {}

  // 对话连接成功事件 (chat.created)
  public void onChatCreated(WebsocketsChatClient client, ChatCreatedEvent event) {}

  // 对话配置成功事件 (chat.updated)
  public void onChatUpdated(WebsocketsChatClient client, ChatUpdatedEvent event) {}

  // 对话创建事件 (conversation.chat.created)
  public void onConversationChatCreated(
      WebsocketsChatClient client, ConversationChatCreatedEvent event) {}

  // 对话正在处理事件 (conversation.chat.in_progress)
  public void onConversationChatInProgress(
      WebsocketsChatClient client, ConversationChatInProgressEvent event) {}

  // 增量消息事件 (conversation.message.delta)
  public void onConversationMessageDelta(
      WebsocketsChatClient client, ConversationMessageDeltaEvent event) {}

  // 增量语音事件 (conversation.audio.delta)
  public void onConversationAudioDelta(
      WebsocketsChatClient client, ConversationAudioDeltaEvent event) {}

  // 消息完成事件 (conversation.message.completed)
  public void onConversationMessageCompleted(
      WebsocketsChatClient client, ConversationMessageCompletedEvent event) {}

  // 语音回复完成事件 (conversation.audio.completed)
  public void onConversationAudioCompleted(
      WebsocketsChatClient client, ConversationAudioCompletedEvent event) {}

  // 对话完成事件 (conversation.chat.completed)
  public void onConversationChatCompleted(
      WebsocketsChatClient client, ConversationChatCompletedEvent event) {}

  // 对话失败事件 (conversation.chat.failed)
  public void onConversationChatFailed(
      WebsocketsChatClient client, ConversationChatFailedEvent event) {}

  // 语音提交成功事件 (input_audio_buffer.completed)
  public void onInputAudioBufferCompleted(
      WebsocketsChatClient client, InputAudioBufferCompletedEvent event) {}

  // 语音清除成功事件 (input_audio_buffer.cleared)
  public void onInputAudioBufferCleared(
      WebsocketsChatClient client, InputAudioBufferClearedEvent event) {}

  // 对话清除事件 (conversation.cleared)
  public void onConversationCleared(WebsocketsChatClient client, ConversationClearedEvent event) {}

  // 对话取消事件 (conversation.chat.canceled)
  public void onConversationChatCanceled(
      WebsocketsChatClient client, ConversationChatCanceledEvent event) {}

  // 语音转录更新事件 (conversation.audio_transcript.update)
  public void onConversationAudioTranscriptUpdate(
      WebsocketsChatClient client, ConversationAudioTranscriptUpdateEvent event) {}

  // 语音转录完成事件 (conversation.audio_transcript.completed)
  public void onConversationAudioTranscriptCompleted(
      WebsocketsChatClient client, ConversationAudioTranscriptCompletedEvent event) {}

  // 端插件事件 (conversation.chat.requires_action)
  public void onConversationChatRequiresAction(
      WebsocketsChatClient client, ConversationChatRequiresActionEvent event) {}

  // 服务端检测到开始说话事件（input_audio_buffer.speech_started）
  public void onInputAudioBufferSpeechStarted(
      WebsocketsChatClient client, InputAudioBufferSpeechStartedEvent event) {}

  // 服务端检测到停止说话事件（input_audio_buffer.speech_stopped）
  public void onInputAudioBufferSpeechStopped(
      WebsocketsChatClient client, InputAudioBufferSpeechStoppedEvent event) {}
}
