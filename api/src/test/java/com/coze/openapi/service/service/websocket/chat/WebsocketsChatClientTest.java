package com.coze.openapi.service.service.websocket.chat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.client.chat.model.ChatToolCall;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.websocket.event.EventType;
import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.ChatUpdateEventData;
import com.coze.openapi.client.websocket.event.upstream.ConversationChatSubmitToolOutputsEvent;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class WebsocketsChatClientTest {
  @Mock private OkHttpClient mockOkHttpClient;
  @Mock private WebSocket mockWebSocket;
  @Mock private WebsocketsChatCallbackHandler mockCallbackHandler;

  @Captor private ArgumentCaptor<ChatCreatedEvent> chatCreatedEventCaptor;
  @Captor private ArgumentCaptor<ConversationMessageDeltaEvent> messageDeltaEventCaptor;
  @Captor private ArgumentCaptor<ConversationChatRequiresActionEvent> requiresActionEventCaptor;
  @Captor private ArgumentCaptor<ChatUpdatedEvent> chatUpdatedEventCaptor;
  @Captor private ArgumentCaptor<ConversationAudioCompletedEvent> audioCompletedEventCaptor;
  @Captor private ArgumentCaptor<ConversationAudioDeltaEvent> audioDeltaEventCaptor;
  @Captor private ArgumentCaptor<ConversationMessageDeltaEvent> conversationMessageDeltaEventCaptor;

  @Captor
  private ArgumentCaptor<ConversationAudioTranscriptUpdateEvent> audioTranscriptUpdateEventCaptor;

  @Captor private ArgumentCaptor<InputAudioBufferClearedEvent> inputAudioBufferClearedEventCaptor;

  @Captor
  private ArgumentCaptor<InputAudioBufferCompletedEvent> inputAudioBufferCompletedEventCaptor;

  @Captor private ArgumentCaptor<ErrorEvent> errorEventCaptor;

  @Captor
  private ArgumentCaptor<ConversationAudioTranscriptCompletedEvent>
      audioTranscriptCompletedEventCaptor;

  @Captor private ArgumentCaptor<ConversationChatCompletedEvent> chatCompletedEventCaptor;
  @Captor private ArgumentCaptor<ConversationChatCreatedEvent> conversationChatCreatedEventCaptor;
  @Captor private ArgumentCaptor<ConversationChatFailedEvent> chatFailedEventCaptor;
  @Captor private ArgumentCaptor<ConversationChatInProgressEvent> chatInProgressEventCaptor;
  @Captor private ArgumentCaptor<ConversationMessageCompletedEvent> messageCompletedEventCaptor;
  // ... 其他事件的 Captor

  private WebsocketsChatClient client;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    when(mockOkHttpClient.newWebSocket(any(), any())).thenReturn(mockWebSocket);

    WebsocketsChatCreateReq req =
        WebsocketsChatCreateReq.builder()
            .botID("test-bot-id")
            .callbackHandler(mockCallbackHandler)
            .build();

    client = new WebsocketsChatClient(mockOkHttpClient, "ws://test.com", req);
  }

  @Test
  public void testHandleChatCreatedEvent() {
    // event_type: chat.created
    String json =
        "{\n"
            + "    \"id\": \"7446668538246561xxxx\",\n"
            + "    \"event_type\": \"chat.created\",\n"
            + "    \"detail\": {\n"
            + "        \"logid\": \"20241210152726467C48D89D6DB2F3***\"    }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler).onChatCreated(eq(client), chatCreatedEventCaptor.capture());

    ChatCreatedEvent event = chatCreatedEventCaptor.getValue();
    assertEquals(EventType.CHAT_CREATED, event.getEventType());
  }

  @Test
  public void testHandleChatUpdatedEvent() {
    // event_type: chat.updated
    String json =
        "{\n"
            + "  \"id\":\"event_id\",\n"
            + "  \"event_type\":\"chat.updated\",\n"
            + "  \"data\":{\n"
            + "    \"chat_config\":{\n"
            + "      \"auto_save_history\":true,\n"
            + "      \"conversation_id\":\"xxxx\",\n"
            + "      \"user_id\":\"xxx\",\n"
            + "      \"meta_data\":{ },\n"
            + "      \"custom_variables\":{ },\n"
            + "      \"extra_params\":{ },\n"
            + "      \"parameters\":{\n"
            + "        \"custom_var_1\":\"测试\"\n"
            + "      }\n"
            + "    },\n"
            + "    \"input_audio\":{\n"
            + "      \"format\":\"pcm\",\n"
            + "      \"codec\":\"pcm\",\n"
            + "      \"sample_rate\":24000,\n"
            + "      \"channel\":1,\n"
            + "      \"bit_depth\":16\n"
            + "    },\n"
            + "    \"output_audio\":{\n"
            + "      \"codec\":\"opus\",\n"
            + "      \"opus_config\":{\n"
            + "        \"bitrate\":48000,\n"
            + "        \"use_cbr\":false,\n"
            + "        \"frame_size_ms\":10,\n"
            + "        \"limit_config\":{\n"
            + "          \"period\":2,\n"
            + "          \"max_frame_num\":300\n"
            + "        }\n"
            + "      },\n"
            + "      \"speech_rate\":50,\n"
            + "      \"voice_id\":\"74466752759302*****\"\n"
            + "    }\n"
            + "  },\n"
            + "  \"detail\":{\n"
            + "    \"logid\":\"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler).onChatUpdated(eq(client), chatUpdatedEventCaptor.capture());

    ChatUpdatedEvent event = chatUpdatedEventCaptor.getValue();
    assertEquals(EventType.CHAT_UPDATED, event.getEventType());
    assertEquals("event_id", event.getId());

    // 验证 chat_config
    assertEquals(true, event.getData().getChatConfig().getAutoSaveHistory());
    assertEquals("xxxx", event.getData().getChatConfig().getConversationId());
    assertEquals("xxx", event.getData().getChatConfig().getUserId());

    // 验证 input_audio
    assertEquals("pcm", event.getData().getInputAudio().getFormat());
    assertEquals("pcm", event.getData().getInputAudio().getCodec());
    assertEquals(24000, event.getData().getInputAudio().getSampleRate());
    assertEquals(1, event.getData().getInputAudio().getChannel());
    assertEquals(16, event.getData().getInputAudio().getBitDepth());

    // 验证 output_audio
    assertEquals("opus", event.getData().getOutputAudio().getCodec());
    assertEquals(48000, event.getData().getOutputAudio().getOpusConfig().getBitrate());
    assertEquals(false, event.getData().getOutputAudio().getOpusConfig().getUseCbr());
    assertEquals(10, event.getData().getOutputAudio().getOpusConfig().getFrameSizeMs());
    assertEquals(2, event.getData().getOutputAudio().getOpusConfig().getLimitConfig().getPeriod());
    assertEquals(
        300, event.getData().getOutputAudio().getOpusConfig().getLimitConfig().getMaxFrameNum());
    assertEquals(50, event.getData().getOutputAudio().getSpeechRate());
    assertEquals("74466752759302*****", event.getData().getOutputAudio().getVoiceId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleConversationAudioCompletedEvent() {
    // event_type: conversation.audio.completed
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"conversation.audio.completed\",\n"
            + "  \"data\": {\n"
            + "    \"id\": \"msg_002\",\n"
            + "    \"role\": \"assistant\",\n"
            + "    \"type\": \"function_call\",\n"
            + "    \"content\": \"{\\\"name\\\":\\\"toutiaosousuo-search\\\",\\\"arguments\\\":{\\\"cursor\\\":0,\\\"input_query\\\":\\\"今天的体育新闻\\\",\\\"plugin_id\\\":7281192623887548473,\\\"api_id\\\":7288907006982012986,\\\"plugin_type\\\":1}}\",\n"
            + "    \"content_type\": \"audio\",\n"
            + "    \"chat_id\": \"123\",\n"
            + "    \"conversation_id\": \"123\",\n"
            + "    \"bot_id\": \"222\"\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationAudioCompleted(eq(client), audioCompletedEventCaptor.capture());

    ConversationAudioCompletedEvent event = audioCompletedEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_AUDIO_COMPLETED, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 data
    assertEquals("msg_002", event.getData().getId());
    assertEquals("assistant", event.getData().getRole().getValue());
    assertEquals("function_call", event.getData().getType().getValue());
    assertEquals("audio", event.getData().getContentType().getValue());
    assertEquals("123", event.getData().getChatId());
    assertEquals("123", event.getData().getConversationId());
    assertEquals("222", event.getData().getBotId());

    // 验证 content 中的 function_call 数据
    String content = event.getData().getContent();
    assertTrue(content.contains("toutiaosousuo-search"));
    assertTrue(content.contains("今天的体育新闻"));
    assertTrue(content.contains("7281192623887548473"));
    assertTrue(content.contains("7288907006982012986"));

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleConversationAudioDeltaEvent() {
    // event_type: conversation.audio.delta
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"conversation.audio.delta\",\n"
            + "  \"data\": {\n"
            + "      \"id\": \"msg_006\",\n"
            + "      \"role\": \"assistant\",\n"
            + "      \"type\": \"answer\",\n"
            + "      \"content\": \"你好你好\",\n"
            + "      \"content_type\": \"text\",\n"
            + "      \"chat_id\": \"123\",\n"
            + "      \"conversation_id\": \"123\",\n"
            + "      \"bot_id\": \"222\"\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationAudioDelta(eq(client), audioDeltaEventCaptor.capture());

    ConversationAudioDeltaEvent event = audioDeltaEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_AUDIO_DELTA, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 data
    assertEquals("msg_006", event.getData().getId());
    assertEquals("assistant", event.getData().getRole().getValue());
    assertEquals("answer", event.getData().getType().getValue());
    assertEquals("你好你好", event.getData().getContent());
    assertEquals("text", event.getData().getContentType().getValue());
    assertEquals("123", event.getData().getChatId());
    assertEquals("123", event.getData().getConversationId());
    assertEquals("222", event.getData().getBotId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleConversationAudioTranscriptCompletedEvent() {
    // event_type: conversation.audio_transcript.completed
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"conversation.audio_transcript.completed\",\n"
            + "  \"data\": {\n"
            + "      \"content\": \"今天的天气怎么样？\"\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationAudioTranscriptCompleted(
            eq(client), audioTranscriptCompletedEventCaptor.capture());

    ConversationAudioTranscriptCompletedEvent event =
        audioTranscriptCompletedEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_AUDIO_TRANSCRIPT_COMPLETED, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 data
    assertEquals("今天的天气怎么样？", event.getData().getContent());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleConversationAudioTranscriptUpdateEvent() {
    // event_type: conversation.audio_transcript.update
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"conversation.audio_transcript.update\",\n"
            + "  \"data\": {\n"
            + "      \"content\": \"今天的\"\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationAudioTranscriptUpdate(
            eq(client), audioTranscriptUpdateEventCaptor.capture());

    ConversationAudioTranscriptUpdateEvent event = audioTranscriptUpdateEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_AUDIO_TRANSCRIPT_UPDATE, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 data
    assertEquals("今天的", event.getData().getContent());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleConversationChatCanceledEvent() {
    // event_type: conversation.chat.canceled
    String json = "{\"event_type\":\"conversation.chat.canceled\"}";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationChatCanceled(eq(client), any(ConversationChatCanceledEvent.class));
  }

  @Test
  public void testHandleConversationChatCompletedEvent() {
    // event_type: conversation.chat.completed
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"conversation.chat.completed\",\n"
            + "  \"data\": {\n"
            + "    \"id\": \"123\",\n"
            + "    \"chat_id\": \"123\",\n"
            + "    \"conversation_id\": \"123\",\n"
            + "    \"bot_id\": \"222\",\n"
            + "    \"created_at\": 1710348675,\n"
            + "    \"completed_at\": 1710348675,\n"
            + "    \"last_error\": null,\n"
            + "    \"meta_data\": {},\n"
            + "    \"status\": \"completed\",\n"
            + "    \"usage\": {\n"
            + "        \"token_count\": 3397,\n"
            + "        \"output_tokens\": 1173,\n"
            + "        \"input_tokens\": 2224\n"
            + "    }\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationChatCompleted(eq(client), chatCompletedEventCaptor.capture());

    ConversationChatCompletedEvent event = chatCompletedEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_CHAT_COMPLETED, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 data
    assertEquals("123", event.getData().getID());
    assertEquals("123", event.getData().getConversationID());
    assertEquals("222", event.getData().getBotID());
    assertEquals(1710348675, event.getData().getCreatedAt());
    assertEquals(1710348675, event.getData().getCompletedAt());
    assertNull(event.getData().getLastError());
    assertEquals("completed", event.getData().getStatus().getValue());

    // 验证 usage
    assertEquals(3397, event.getData().getUsage().getTokenCount());
    assertEquals(1173, event.getData().getUsage().getOutputTokens());
    assertEquals(2224, event.getData().getUsage().getInputTokens());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleConversationChatCreatedEvent() {
    // event_type: conversation.chat.created
    String json =
        "{\n"
            + "  \"id\": \"744666853824656xxx\",\n"
            + "  \"event_type\": \"conversation.chat.created\",\n"
            + "  \"data\": {\n"
            + "      \"id\": \"123\",\n"
            + "      \"conversation_id\": \"123\",\n"
            + "      \"bot_id\": \"222\",\n"
            + "      \"created_at\": 1710348675,\n"
            + "      \"completed_at\": null,\n"
            + "      \"last_error\": null,\n"
            + "      \"meta_data\": {},\n"
            + "      \"status\": \"created\",\n"
            + "      \"usage\": null\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationChatCreated(eq(client), conversationChatCreatedEventCaptor.capture());

    ConversationChatCreatedEvent event = conversationChatCreatedEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_CHAT_CREATED, event.getEventType());
    assertEquals("744666853824656xxx", event.getId());

    // 验证 data
    assertEquals("123", event.getData().getID());
    assertEquals("123", event.getData().getConversationID());
    assertEquals("222", event.getData().getBotID());
    assertEquals(1710348675, event.getData().getCreatedAt());
    assertNull(event.getData().getCompletedAt());
    assertNull(event.getData().getLastError());
    assertEquals("created", event.getData().getStatus().getValue());
    assertNull(event.getData().getUsage());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleConversationChatFailedEvent() {
    // event_type: conversation.chat.failed
    String json =
        "{\n"
            + "    \"id\": \"event_1\",\n"
            + "    \"event_type\": \"conversation.chat.failed\",\n"
            + "    \"data\": {\n"
            + "        \"id\": \"123\",\n"
            + "        \"chat_id\": \"123\",\n"
            + "        \"conversation_id\": \"123\",\n"
            + "        \"bot_id\": \"222\",\n"
            + "        \"created_at\": 1710348675,\n"
            + "        \"failed_at\": 1710348675,\n"
            + "        \"last_error\": {\n"
            + "            \"code\": 1,\n"
            + "            \"msg\": \"发生异常\"\n"
            + "        },\n"
            + "        \"meta_data\": { },\n"
            + "        \"status\": \"failed\",\n"
            + "        \"usage\": {\n"
            + "            \"token_count\": 3397,\n"
            + "            \"output_tokens\": 1173,\n"
            + "            \"input_tokens\": 2224\n"
            + "        }\n"
            + "    },\n"
            + "    \"detail\": {\n"
            + "        \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "    }\n"
            + "}";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationChatFailed(eq(client), chatFailedEventCaptor.capture());

    ConversationChatFailedEvent event = chatFailedEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_CHAT_FAILED, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 data
    assertEquals("123", event.getData().getID());
    assertEquals("123", event.getData().getConversationID());
    assertEquals("222", event.getData().getBotID());
    assertEquals(1710348675, event.getData().getCreatedAt());
    assertEquals(1710348675, event.getData().getFailedAt());
    assertEquals(1, event.getData().getLastError().getCode());
    assertEquals("发生异常", event.getData().getLastError().getMsg());
    assertEquals("failed", event.getData().getStatus().getValue());

    // 验证 usage
    assertEquals(3397, event.getData().getUsage().getTokenCount());
    assertEquals(1173, event.getData().getUsage().getOutputTokens());
    assertEquals(2224, event.getData().getUsage().getInputTokens());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleConversationChatInProgressEvent() {
    // event_type: conversation.chat.in_progress
    String json =
        "{\n"
            + "  \"id\": \"744666853824656xxxx\",\n"
            + "  \"event_type\": \"conversation.chat.in_progress\",\n"
            + "  \"data\": {\n"
            + "      \"id\": \"123\",\n"
            + "      \"conversation_id\": \"123\",\n"
            + "      \"bot_id\": \"222\",\n"
            + "      \"created_at\": 1710348675,\n"
            + "      \"completed_at\": null,\n"
            + "      \"last_error\": null,\n"
            + "      \"meta_data\": {},\n"
            + "      \"status\": \"in_progress\",\n"
            + "      \"usage\": null\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationChatInProgress(eq(client), chatInProgressEventCaptor.capture());

    ConversationChatInProgressEvent event = chatInProgressEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_CHAT_IN_PROGRESS, event.getEventType());
    assertEquals("744666853824656xxxx", event.getId());

    // 验证 data
    assertEquals("123", event.getData().getID());
    assertEquals("123", event.getData().getConversationID());
    assertEquals("222", event.getData().getBotID());
    assertEquals(1710348675, event.getData().getCreatedAt());
    assertNull(event.getData().getCompletedAt());
    assertNull(event.getData().getLastError());
    assertEquals("in_progress", event.getData().getStatus().getValue());
    assertNull(event.getData().getUsage());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleConversationChatRequiresActionEvent() {
    // event_type: conversation.chat.requires_action
    String json =
        "{\"event_type\":\"conversation.chat.requires_action\","
            + "\"data\":{"
            + "\"required_action\":{"
            + "\"type\":\"submit_tool_outputs\","
            + "\"submit_tool_outputs\":{"
            + "\"tool_calls\":[{"
            + "\"id\":\"call-id\","
            + "\"type\":\"function\","
            + "\"function\":{"
            + "\"name\":\"get_weather\","
            + "\"arguments\":\"{\\\"location\\\":\\\"深圳\\\"}\""
            + "}"
            + "}]"
            + "}"
            + "}"
            + "}}";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationChatRequiresAction(eq(client), requiresActionEventCaptor.capture());

    ConversationChatRequiresActionEvent event = requiresActionEventCaptor.getValue();
    assertEquals("submit_tool_outputs", event.getData().getRequiredAction().getType().getValue());

    List<ChatToolCall> toolCalls =
        event.getData().getRequiredAction().getSubmitToolOutputs().getToolCalls();
    assertEquals(1, toolCalls.size());

    ChatToolCall toolCall = toolCalls.get(0);
    assertEquals("call-id", toolCall.getID());
    assertEquals("function", toolCall.getType().getValue());
    assertEquals("get_weather", toolCall.getFunction().getName());
    assertTrue(toolCall.getFunction().getArguments().contains("location"));
    assertTrue(toolCall.getFunction().getArguments().contains("深圳"));
  }

  @Test
  public void testHandleConversationClearedEvent() {
    // event_type: conversation.cleared
    String json = "{\"event_type\":\"conversation.cleared\"}";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationCleared(eq(client), any(ConversationClearedEvent.class));
  }

  @Test
  public void testHandleConversationMessageCompletedEvent() {
    // event_type: conversation.message.completed
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"conversation.message.completed\",\n"
            + "  \"data\": {\n"
            + "    \"id\": \"msg_002\",\n"
            + "    \"role\": \"assistant\",\n"
            + "    \"type\": \"function_call\",\n"
            + "    \"content\": \"{\\\"name\\\":\\\"toutiaosousuo-search\\\",\\\"arguments\\\":{\\\"cursor\\\":0,\\\"input_query\\\":\\\"今天的体育新闻\\\",\\\"plugin_id\\\":7281192623887548473,\\\"api_id\\\":7288907006982012986,\\\"plugin_type\\\":1}}\",\n"
            + "    \"content_type\": \"text\",\n"
            + "    \"chat_id\": \"123\",\n"
            + "    \"conversation_id\": \"123\",\n"
            + "    \"bot_id\": \"222\"\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationMessageCompleted(eq(client), messageCompletedEventCaptor.capture());

    ConversationMessageCompletedEvent event = messageCompletedEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_MESSAGE_COMPLETED, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 data
    assertEquals("msg_002", event.getData().getId());
    assertEquals("assistant", event.getData().getRole().getValue());
    assertEquals("function_call", event.getData().getType().getValue());
    assertEquals("text", event.getData().getContentType().getValue());
    assertEquals("123", event.getData().getChatId());
    assertEquals("123", event.getData().getConversationId());
    assertEquals("222", event.getData().getBotId());

    // 验证 content 中的 function_call 数据
    String content = event.getData().getContent();
    assertTrue(content.contains("toutiaosousuo-search"));
    assertTrue(content.contains("今天的体育新闻"));
    assertTrue(content.contains("7281192623887548473"));
    assertTrue(content.contains("7288907006982012986"));

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleInputAudioBufferClearedEvent() {
    // event_type: input_audio_buffer.cleared
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"input_audio_buffer.cleared\",\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onInputAudioBufferCleared(eq(client), inputAudioBufferClearedEventCaptor.capture());

    InputAudioBufferClearedEvent event = inputAudioBufferClearedEventCaptor.getValue();
    assertEquals(EventType.INPUT_AUDIO_BUFFER_CLEARED, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleInputAudioBufferCompletedEvent() {
    // event_type: input_audio_buffer.completed
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"input_audio_buffer.completed\",\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onInputAudioBufferCompleted(eq(client), inputAudioBufferCompletedEventCaptor.capture());

    InputAudioBufferCompletedEvent event = inputAudioBufferCompletedEventCaptor.getValue();
    assertEquals(EventType.INPUT_AUDIO_BUFFER_COMPLETED, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleErrorEvent() {
    // event_type: error
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"error\",\n"
            + "  \"data\": {\n"
            + "      \"code\": 123,\n"
            + "      \"msg\": \"error message\"\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler).onError(eq(client), errorEventCaptor.capture());

    ErrorEvent event = errorEventCaptor.getValue();
    assertEquals(EventType.ERROR, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 data
    assertEquals(123, event.getData().getCode());
    assertEquals("error message", event.getData().getMsg());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleUnknownEvent() {
    // event_type: unknown
    String json = "{\"event_type\":\"unknown\"}";

    client.handleEvent(mockWebSocket, json);

    verifyNoInteractions(mockCallbackHandler);
  }

  @Test
  public void testHandleConversationMessageDeltaEvent() {
    String json =
        "{\n"
            + "  \"id\": \"event_1\",\n"
            + "  \"event_type\": \"conversation.message.delta\",\n"
            + "  \"data\": {\n"
            + "      \"id\": \"msg_006\",\n"
            + "      \"role\": \"assistant\",\n"
            + "      \"type\": \"answer\",\n"
            + "      \"content\": \"你好你好\",\n"
            + "      \"content_type\": \"text\",\n"
            + "      \"chat_id\": \"123\",\n"
            + "      \"conversation_id\": \"123\",\n"
            + "      \"bot_id\": \"222\"\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onConversationMessageDelta(eq(client), conversationMessageDeltaEventCaptor.capture());

    ConversationMessageDeltaEvent event = conversationMessageDeltaEventCaptor.getValue();
    assertEquals(EventType.CONVERSATION_MESSAGE_DELTA, event.getEventType());
    assertEquals("event_1", event.getId());

    // 验证 data
    assertEquals("msg_006", event.getData().getId());
    assertEquals("assistant", event.getData().getRole().getValue());
    assertEquals("answer", event.getData().getType().getValue());
    assertEquals("你好你好", event.getData().getContent());
    assertEquals("text", event.getData().getContentType().getValue());
    assertEquals("123", event.getData().getChatId());
    assertEquals("123", event.getData().getConversationId());
    assertEquals("222", event.getData().getBotId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleInvalidJson() {
    String invalidJson = "invalid json";

    client.handleEvent(mockWebSocket, invalidJson);

    verify(mockCallbackHandler).onClientException(eq(client), any(RuntimeException.class));
  }

  @Test
  void testChatUpdate() {
    ChatUpdateEventData data = ChatUpdateEventData.builder().build();

    client.chatUpdate(data);

    verify(mockWebSocket).send(anyString()); // 验证发送了消息
  }

  @Test
  void testConversationChatCancel() {
    client.conversationChatCancel();

    verify(mockWebSocket).send(anyString());
  }

  @Test
  void testConversationChatSubmitToolOutputs() {
    ConversationChatSubmitToolOutputsEvent.Data data =
        ConversationChatSubmitToolOutputsEvent.Data.builder().chatID("test-tool-call-id").build();

    client.conversationChatSubmitToolOutputs(data);

    verify(mockWebSocket).send(anyString());
  }

  @Test
  void testConversationClear() {
    client.conversationClear();

    verify(mockWebSocket).send(anyString());
  }

  @Test
  void testConversationMessageCreate() {
    client.conversationMessageCreate(Message.buildUserQuestionText("hello"));

    verify(mockWebSocket).send(anyString());
  }

  @Test
  void testInputAudioBufferAppendWithData() {

    client.inputAudioBufferAppend("hello");

    verify(mockWebSocket).send(anyString());
  }

  @Test
  public void testHandleInputAudioBufferSpeechStarted() {
    // event_type: conversation.cleared
    String json = "{\"event_type\":\"input_audio_buffer.speech_started\"}";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onInputAudioBufferSpeechStarted(eq(client), any(InputAudioBufferSpeechStartedEvent.class));
  }

  @Test
  public void testHandleInputAudioBufferSpeechStopped() {
    // event_type: conversation.cleared
    String json = "{\"event_type\":\"input_audio_buffer.speech_stopped\"}";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onInputAudioBufferSpeechStopped(eq(client), any(InputAudioBufferSpeechStoppedEvent.class));
  }

  @Test
  void testInputAudioBufferAppendWithString() {
    String audioData = "base64EncodedAudioData";

    client.inputAudioBufferAppend(audioData);

    verify(mockWebSocket).send(anyString());
  }

  @Test
  void testInputAudioBufferClear() {
    client.inputAudioBufferClear();

    verify(mockWebSocket).send(anyString());
  }

  @Test
  void testInputAudioBufferComplete() {
    client.inputAudioBufferComplete();

    verify(mockWebSocket).send(anyString());
  }
}
