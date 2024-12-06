package com.coze.openapi.api;

import com.coze.openapi.client.audio.speech.CreateSpeechReq;
import com.coze.openapi.client.common.BaseReq;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Tag;

public interface AudioSpeechAPI {
    @POST("/v1/audio/speech")
    Single<Response<ResponseBody>> create(@Body CreateSpeechReq request, @Tag BaseReq baseReq);
}
