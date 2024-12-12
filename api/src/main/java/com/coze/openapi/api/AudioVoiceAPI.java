package com.coze.openapi.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import com.coze.openapi.client.audio.voices.CloneVoiceResp;
import com.coze.openapi.client.audio.voices.ListVoiceResp;
import com.coze.openapi.client.common.BaseResponse;

import io.reactivex.Single;

public interface AudioVoiceAPI {
    @Multipart
    @POST("/v1/audio/voices/clone")
    Single<BaseResponse<CloneVoiceResp>> cloneVoice(
        @Part MultipartBody.Part file,
        @Part("voice_name") RequestBody voiceName,
        @Part("audio_format") RequestBody audioFormat,
        @Part("language") RequestBody language,
        @Part("voice_id") RequestBody voiceId,
        @Part("preview_text") RequestBody previewText,
        @Part("text") RequestBody text
    );

    @GET("/v1/audio/voices")
    Single<BaseResponse<ListVoiceResp>> listVoice(
        @Query("filter_system_voice") Boolean filterSystemVoice,
        @Query("page_num") Integer pageNum,
        @Query("page_size") Integer pageSize
    );

}
