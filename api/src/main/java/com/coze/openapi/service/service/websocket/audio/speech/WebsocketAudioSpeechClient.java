package com.coze.openapi.service.service.websocket.audio.speech;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.SpeechEventUpdateEventData;
import com.coze.openapi.client.websocket.event.upstream.*;
import com.coze.openapi.service.service.websocket.common.BaseWebSocketListener;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class WebsocketAudioSpeechClient {
    private final ObjectMapper objectMapper = Utils.getMapper();
    private final WebSocket ws;
    private final WebsocketAudioSpeechCallbackHandler handler;
    private static final String uri = "/v1/audio/speech";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public WebsocketAudioSpeechClient(OkHttpClient client, String wsHost, WebsocketAudioSpeechCallbackHandler handler) {
        String url = String.format("%s%s?voice_id=%s", wsHost, uri, "req.getVoiceID()");
        Request request = new Request.Builder().url(url).build();
        this.handler = handler;
        this.ws = client.newWebSocket(request, new BaseWebSocketListener<>(this::handleEvent, this.handler, this));
    }

    private void sendEvent(BaseEvent event) {
        this.ws.send(Utils.toJson(event));
    }

    // 发送语音配置更新事件
    public void speechUpdate(SpeechEventUpdateEventData data) {
        this.sendEvent(SpeechUpdateEvent.builder().data(data).build());
    }

    // 发送文本缓冲区追加事件
    public void inputTextBufferAppend(String data) {
        this.sendEvent(InputTextBufferAppendEvent.of(data));
    }

    // 发送文本缓冲区完成事件
    public void inputTextBufferComplete() {
        this.sendEvent(new InputTextBufferCompleteEvent());
    }

    void handleEvent(WebSocket ws, String text) {
        try {
            JsonNode jsonNode = objectMapper.readTree(text);
            String eventType = jsonNode.get("event_type").asText();

            switch (eventType) {
                case EventType.SPEECH_CREATED:
                    SpeechCreatedEvent speechCreatedEvent = 
                        objectMapper.treeToValue(jsonNode, SpeechCreatedEvent.class);
                    handler.onSpeechCreated(WebsocketAudioSpeechClient.this, speechCreatedEvent);
                    break;
                case EventType.SPEECH_UPDATED:
                    SpeechUpdatedEvent speechUpdatedEvent = 
                        objectMapper.treeToValue(jsonNode, SpeechUpdatedEvent.class);
                    handler.onSpeechUpdated(WebsocketAudioSpeechClient.this, speechUpdatedEvent);
                    break;
                case EventType.SPEECH_AUDIO_UPDATE:
                    SpeechAudioUpdateEvent audioUpdateEvent = 
                        objectMapper.treeToValue(jsonNode, SpeechAudioUpdateEvent.class);
                    handler.onSpeechAudioUpdate(WebsocketAudioSpeechClient.this, audioUpdateEvent);
                    break;
                case EventType.SPEECH_AUDIO_COMPLETED:
                    SpeechAudioCompletedEvent audioCompletedEvent = 
                        objectMapper.treeToValue(jsonNode, SpeechAudioCompletedEvent.class);
                    handler.onSpeechAudioCompleted(WebsocketAudioSpeechClient.this, audioCompletedEvent);
                    break;
                case EventType.INPUT_TEXT_BUFFER_COMPLETED:
                    InputTextBufferCompletedEvent bufferCompletedEvent = 
                        objectMapper.treeToValue(jsonNode, InputTextBufferCompletedEvent.class);
                    handler.onInputTextBufferCompleted(WebsocketAudioSpeechClient.this, bufferCompletedEvent);
                    break;
                case EventType.ERROR:
                    ErrorEvent errorEvent = objectMapper.treeToValue(jsonNode, ErrorEvent.class);
                    handler.onError(WebsocketAudioSpeechClient.this, errorEvent);
                    break;
                default:
                    System.out.println("未知事件类型: " + eventType);
            }
        } catch (Exception e) {
            handler.onClientException(WebsocketAudioSpeechClient.this, new RuntimeException(e));
        }
    }

    public void close() {
        this.ws.close(1000, null);
        executorService.shutdown();
    }
}
