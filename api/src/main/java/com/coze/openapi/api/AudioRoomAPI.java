package com.coze.openapi.api;

import com.coze.openapi.client.audio.rooms.CreateRoomReq;
import com.coze.openapi.client.audio.rooms.CreateRoomResp;
import com.coze.openapi.client.common.BaseResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AudioRoomAPI {
    @POST("/v1/audio/rooms")
    Single<BaseResponse<CreateRoomResp>> createRoom(@Body CreateRoomReq request);
}
