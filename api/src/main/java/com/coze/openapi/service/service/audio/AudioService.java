package com.coze.openapi.service.service.audio;

import com.coze.openapi.api.AudioRoomAPI;
import com.coze.openapi.api.AudioSpeechAPI;
import com.coze.openapi.api.AudioVoiceAPI;

public class AudioService {
    private final VoiceService voiceApi;
    private final RoomService roomApi;
    private final SpeechService speechApi;

    public AudioService(AudioVoiceAPI voiceApi, AudioRoomAPI roomApi, AudioSpeechAPI speechApi) {
        this.voiceApi = new VoiceService(voiceApi);
        this.roomApi = new RoomService(roomApi);
        this.speechApi = new SpeechService(speechApi);
    }

    public VoiceService voice() {
        return this.voiceApi;
    }

    public RoomService room() {
        return this.roomApi;
    }

    public SpeechService speech() {
        return this.speechApi;
    }

}
