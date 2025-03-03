package com.coze.openapi.service.service.websocket.audio.transcriptions;

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

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class WebsocketAudioTranscriptionsClientTest {
  @Mock private OkHttpClient mockOkHttpClient;
  @Mock private WebSocket mockWebSocket;
  @Mock private WebsocketAudioTranscriptionsCallbackHandler mockCallbackHandler;

  @Captor private ArgumentCaptor<TranscriptionsCreatedEvent> transcriptionsCreatedEventCaptor;
  @Captor private ArgumentCaptor<TranscriptionsUpdatedEvent> transcriptionsUpdatedEventCaptor;

  @Captor
  private ArgumentCaptor<TranscriptionsMessageUpdateEvent> transcriptionsMessageUpdateEventCaptor;

  @Captor
  private ArgumentCaptor<TranscriptionsMessageCompletedEvent>
      transcriptionsMessageCompletedEventCaptor;

  @Captor private ArgumentCaptor<InputAudioBufferClearedEvent> inputAudioBufferClearedEventCaptor;

  @Captor
  private ArgumentCaptor<InputAudioBufferCompletedEvent> inputAudioBufferCompletedEventCaptor;

  @Captor private ArgumentCaptor<ErrorEvent> errorEventCaptor;

  private WebsocketAudioTranscriptionsClient client;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    when(mockOkHttpClient.newWebSocket(any(), any())).thenReturn(mockWebSocket);

    WebsocketAudioTranscriptionsCreateReq req =
        WebsocketAudioTranscriptionsCreateReq.builder()
            .callbackHandler(mockCallbackHandler)
            .build();
    client = new WebsocketAudioTranscriptionsClient(mockOkHttpClient, "ws://test.com", req);
  }

  @Test
  public void testHandleTranscriptionsCreatedEvent() {
    String json =
        "{\n"
            + "  \"id\": \"7446668538246561xxxx\",\n"
            + "  \"event_type\": \"transcriptions.created\",\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onTranscriptionsCreated(eq(client), transcriptionsCreatedEventCaptor.capture());

    TranscriptionsCreatedEvent event = transcriptionsCreatedEventCaptor.getValue();
    assertEquals(EventType.TRANSCRIPTIONS_CREATED, event.getEventType());
    assertEquals("7446668538246561xxxx", event.getId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleTranscriptionsUpdatedEvent() {
    String json =
        "{\n"
            + "  \"id\": \"event_id\",\n"
            + "  \"event_type\": \"transcriptions.updated\",\n"
            + "  \"data\": {\n"
            + "      \"input_audio\": {\n"
            + "          \"format\": \"pcm\",\n"
            + "          \"codec\": \"pcm\",\n"
            + "          \"sample_rate\": 24000,\n"
            + "          \"channel\": 1,\n"
            + "          \"bit_depth\": 16\n"
            + "      }\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onTranscriptionsUpdated(eq(client), transcriptionsUpdatedEventCaptor.capture());

    TranscriptionsUpdatedEvent event = transcriptionsUpdatedEventCaptor.getValue();
    assertEquals(EventType.TRANSCRIPTIONS_UPDATED, event.getEventType());
    assertEquals("event_id", event.getId());

    // 验证 data
    assertEquals("pcm", event.getData().getInputAudio().getFormat());
    assertEquals("pcm", event.getData().getInputAudio().getCodec());
    assertEquals(24000, event.getData().getInputAudio().getSampleRate());
    assertEquals(1, event.getData().getInputAudio().getChannel());
    assertEquals(16, event.getData().getInputAudio().getBitDepth());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleTranscriptionsMessageUpdateEvent() {
    String json =
        "{\n"
            + "  \"id\": \"event_id\",\n"
            + "  \"event_type\": \"transcriptions.message.update\",\n"
            + "  \"data\": {\n"
            + "      \"content\": \"text\"\n"
            + "  },\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onTranscriptionsMessageUpdate(
            eq(client), transcriptionsMessageUpdateEventCaptor.capture());

    TranscriptionsMessageUpdateEvent event = transcriptionsMessageUpdateEventCaptor.getValue();
    assertEquals(EventType.TRANSCRIPTIONS_MESSAGE_UPDATE, event.getEventType());
    assertEquals("event_id", event.getId());

    // 验证 data
    assertEquals("text", event.getData().getContent());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleTranscriptionsMessageCompletedEvent() {
    String json =
        "{\n"
            + "  \"id\": \"event_id\",\n"
            + "  \"event_type\": \"transcriptions.message.completed\",\n"
            + "  \"detail\": {\n"
            + "      \"logid\": \"20241210152726467C48D89D6DB2F3***\"\n"
            + "  }\n"
            + "}\n";

    client.handleEvent(mockWebSocket, json);

    verify(mockCallbackHandler)
        .onTranscriptionsMessageCompleted(
            eq(client), transcriptionsMessageCompletedEventCaptor.capture());

    TranscriptionsMessageCompletedEvent event =
        transcriptionsMessageCompletedEventCaptor.getValue();
    assertEquals(EventType.TRANSCRIPTIONS_MESSAGE_COMPLETED, event.getEventType());
    assertEquals("event_id", event.getId());

    // 验证 detail
    assertEquals("20241210152726467C48D89D6DB2F3***", event.getDetail().getLogID());
  }

  @Test
  public void testHandleInputAudioBufferClearedEvent() {
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
}
