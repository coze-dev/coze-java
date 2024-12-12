package com.coze.openapi.api;

import com.coze.openapi.client.audio.speech.CreateSpeechReq;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AudioSpeechAPI {
    @POST("/v1/audio/speech")
    Single<ResponseBody> createSpeech(@Body CreateSpeechReq request);
}
