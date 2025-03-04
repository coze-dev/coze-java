package com.coze.openapi.service.service.websocket.audio.speech;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.client.websocket.event.EventType;
import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.OutputAudio;
import com.coze.openapi.client.websocket.event.model.PCMConfig;
import com.coze.openapi.client.websocket.event.model.SpeechUpdateEventData;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class WebsocketAudioSpeechClientTest {
  @Mock private OkHttpClient mockOkHttpClient;
  @Mock private WebSocket mockWebSocket;
  @Mock private WebsocketAudioSpeechCallbackHandler mockCallbackHandler;

  @Captor private ArgumentCaptor<SpeechCreatedEvent> speechCreatedEventCaptor;
  @Captor private ArgumentCaptor<SpeechUpdatedEvent> speechUpdatedEventCaptor;
  @Captor private ArgumentCaptor<SpeechAudioUpdateEvent> speechAudioUpdateEventCaptor;
  @Captor private ArgumentCaptor<SpeechAudioCompletedEvent> speechAudioCompletedEventCaptor;
  @Captor private ArgumentCaptor<InputTextBufferCompletedEvent> inputTextBufferCompletedEventCaptor;
  @Captor private ArgumentCaptor<ErrorEvent> errorEventCaptor;

  private WebsocketAudioSpeechClient client;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    when(mockOkHttpClient.newWebSocket(any(), any())).thenReturn(mockWebSocket);

    WebsocketAudioSpeechCreateReq req =
        WebsocketAudioSpeechCreateReq.builder().callbackHandler(mockCallbackHandler).build();
    client = new WebsocketAudioSpeechClient(mockOkHttpClient, "ws://test.com", req);
  }

  @Test
  public void testHandleSpeechCreatedEvent() {
    String json =
        "{\n"
            + "  \"id\": \"7446668538246561xxxx\",\n"
            + "  \"event_type\": \"speech.created\",\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler).onSpeechCreated(eq(client), speechCreatedEventCaptor.capture());

    SpeechCreatedEvent event = speechCreatedEventCaptor.getValue();
    assertEquals(EventType.SPEECH_CREATED, event.getEventType());
    assertEquals("7446668538246561xxxx", event.getId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleSpeechUpdatedEvent() {
    String json =
        "{\n"
            + "  \"id\": \"event_id\",\n"
            + "  \"event_type\": \"speech.updated\",\n"
            + "  \"data\": {\n"
            + "      \"output_audio\": {\n"
            + "          \"codec\": \"pcm\",\n"
            + "          \"pcm_config\": {\n"
            + "              \"sample_rate\": 24000\n"
            + "          },\n"
            + "          \"speech_rate\": 50,\n"
            + "          \"voice_id\": \"音色id\"\n"
            + "      }\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler).onSpeechUpdated(eq(client), speechUpdatedEventCaptor.capture());

    SpeechUpdatedEvent event = speechUpdatedEventCaptor.getValue();
    assertEquals(EventType.SPEECH_UPDATED, event.getEventType());
    assertEquals("event_id", event.getId());

    // 验证 data
    assertEquals("pcm", event.getData().getOutputAudio().getCodec());
    assertEquals(24000, event.getData().getOutputAudio().getPcmConfig().getSampleRate());
    assertEquals(50, event.getData().getOutputAudio().getSpeechRate());
    assertEquals("音色id", event.getData().getOutputAudio().getVoiceId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleSpeechAudioUpdateEvent() {
    String json =
        "{\n"
            + "  \"id\": \"event_id\",\n"
            + "  \"event_type\": \"speech.audio.update\",\n"
            + "  \"data\": {\n"
            + "      \"delta\": \"base64EncodedAudioDelta\"\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onSpeechAudioUpdate(eq(client), speechAudioUpdateEventCaptor.capture());

    SpeechAudioUpdateEvent event = speechAudioUpdateEventCaptor.getValue();
    assertEquals(EventType.SPEECH_AUDIO_UPDATE, event.getEventType());
    assertEquals("event_id", event.getId());

    // 验证 data
    assertEquals("base64EncodedAudioDelta", event.getData().getDelta());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleSpeechAudioCompletedEvent() {
    String json =
        "{\n"
            + "  \"id\": \"event_id\",\n"
            + "  \"event_type\": \"speech.audio.completed\",\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onSpeechAudioCompleted(eq(client), speechAudioCompletedEventCaptor.capture());

    SpeechAudioCompletedEvent event = speechAudioCompletedEventCaptor.getValue();
    assertEquals(EventType.SPEECH_AUDIO_COMPLETED, event.getEventType());
    assertEquals("event_id", event.getId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleInputTextBufferCompletedEvent() {
    String json =
        "{\n"
            + "  \"id\": \"event_id\",\n"
            + "  \"event_type\": \"input_text_buffer.completed\",\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onInputTextBufferCompleted(eq(client), inputTextBufferCompletedEventCaptor.capture());

    InputTextBufferCompletedEvent event = inputTextBufferCompletedEventCaptor.getValue();
    assertEquals(EventType.INPUT_TEXT_BUFFER_COMPLETED, event.getEventType());
    assertEquals("event_id", event.getId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleErrorEvent() {
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
    String json = "{\"event_type\":\"unknown\"}";

    client.handleEvent(mockWebSocket, json);

    verifyNoInteractions(mockCallbackHandler);
  }

  @Test
  public void testHandleInvalidJson() {
    String invalidJson = "invalid json";

    client.handleEvent(mockWebSocket, invalidJson);

    verify(mockCallbackHandler).onClientException(eq(client), any(RuntimeException.class));
  }

  @Test
  void testSpeechUpdate() {
    SpeechUpdateEventData data =
        SpeechUpdateEventData.builder()
            .outputAudio(
                OutputAudio.builder()
                    .codec("pcm")
                    .pcmConfig(PCMConfig.builder().sampleRate(24000).build())
                    .speechRate(50)
                    .voiceId("test-voice-id")
                    .build())
            .build();

    client.speechUpdate(data);

    verify(mockWebSocket).send(anyString()); // 验证发送了消息
  }

  @Test
  void testInputTextBufferAppend() {
    String textData = "测试文本内容";

    client.inputTextBufferAppend(textData);

    verify(mockWebSocket).send(anyString());
  }

  @Test
  void testInputTextBufferComplete() {
    client.inputTextBufferComplete();

    verify(mockWebSocket).send(anyString());
  }
}
