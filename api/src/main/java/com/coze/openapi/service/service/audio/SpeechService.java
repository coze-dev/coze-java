package com.coze.openapi.service.service.audio;

import com.coze.openapi.api.AudioSpeechAPI;
import com.coze.openapi.client.audio.speech.CreateSpeechReq;
import com.coze.openapi.client.common.FileResponse;
import com.coze.openapi.service.utils.Utils;

public class SpeechService {
    private final AudioSpeechAPI api;

    public SpeechService(AudioSpeechAPI api) {
        this.api = api;
    }

    public FileResponse create(CreateSpeechReq req) {
        return new FileResponse(Utils.execute(api.create(req)));
    }
}
