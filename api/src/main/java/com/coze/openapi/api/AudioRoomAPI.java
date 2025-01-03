package com.coze.openapi.api;

import com.coze.openapi.client.audio.rooms.CreateRoomReq;
import com.coze.openapi.client.audio.rooms.CreateRoomResp;
import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Tag;

public interface AudioRoomAPI {
  @POST("/v1/audio/rooms")
  Call<BaseResponse<CreateRoomResp>> create(@Body CreateRoomReq request, @Tag BaseReq baseReq);
}
