package com.coze.openapi.client.websocket.event.downstream;

import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Base64;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
// event_type: speech.audio.update
public class SpeechAudioUpdateEvent extends BaseEvent {
    @JsonProperty("event_type")
    @Builder.Default
    private final String eventType = EventType.SPEECH_AUDIO_UPDATE;

    @JsonProperty("data")
    private Data data;

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        @JsonProperty("delta")
        private String delta;
    }

    public static SpeechAudioUpdateEvent of(String delta) {
        return builder().data(new SpeechAudioUpdateEvent.Data(delta)).build();
    }

    public byte[] getDelta() {
        return Base64.getDecoder().decode(this.data.delta);
    }
} 