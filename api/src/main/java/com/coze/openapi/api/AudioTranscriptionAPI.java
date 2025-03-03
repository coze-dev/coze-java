package com.coze.openapi.api;

import com.coze.openapi.client.audio.transcriptions.CreateTranscriptionsResp;
import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Tag;

public interface AudioTranscriptionAPI {
  @Multipart
  @POST("/v1/audio/transcriptions")
  Call<BaseResponse<CreateTranscriptionsResp>> create(
      @Part MultipartBody.Part file, @Tag BaseReq baseReq);
}
