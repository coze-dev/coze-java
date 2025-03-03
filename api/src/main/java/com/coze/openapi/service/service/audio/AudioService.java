package com.coze.openapi.service.service.audio;

import com.coze.openapi.api.AudioRoomAPI;
import com.coze.openapi.api.AudioSpeechAPI;
import com.coze.openapi.api.AudioTranscriptionAPI;
import com.coze.openapi.api.AudioVoiceAPI;

public class AudioService {
  private final VoiceService voiceAPI;
  private final RoomService roomAPI;
  private final SpeechService speechAPI;
  private final TranscriptionService transcriptionAPI;

  public AudioService(
      AudioVoiceAPI voiceAPI,
      AudioRoomAPI roomAPI,
      AudioSpeechAPI speechAPI,
      AudioTranscriptionAPI transcriptionAPI) {
    this.voiceAPI = new VoiceService(voiceAPI);
    this.roomAPI = new RoomService(roomAPI);
    this.speechAPI = new SpeechService(speechAPI);
    this.transcriptionAPI = new TranscriptionService(transcriptionAPI);
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

  public TranscriptionService transcription() {
    return this.transcriptionAPI;
  }
}
