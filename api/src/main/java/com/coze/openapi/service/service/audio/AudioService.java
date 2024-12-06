package com.coze.openapi.service.service.audio;

import com.coze.openapi.api.AudioRoomAPI;
import com.coze.openapi.api.AudioSpeechAPI;
import com.coze.openapi.api.AudioVoiceAPI;

public class AudioService {
    private final VoiceService voiceAPI;
    private final RoomService roomAPI;
    private final SpeechService speechAPI;

    public AudioService(AudioVoiceAPI voiceAPI, AudioRoomAPI roomAPI, AudioSpeechAPI speechAPI) {
        this.voiceAPI = new VoiceService(voiceAPI);
        this.roomAPI = new RoomService(roomAPI);
        this.speechAPI = new SpeechService(speechAPI);
    }

    public VoiceService voices() {
        return this.voiceAPI;
    }

    public RoomService rooms() {
        return this.roomAPI;
    }

    public SpeechService speech() {
        return this.speechAPI;
    }

}
