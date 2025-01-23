package com.coze.openapi.service.service.audio;

import com.coze.openapi.api.AudioRoomAPI;
import com.coze.openapi.client.audio.rooms.CreateRoomReq;
import com.coze.openapi.client.audio.rooms.CreateRoomResp;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.service.utils.Utils;

public class RoomService {
  private final AudioRoomAPI roomApi;

  public RoomService(AudioRoomAPI roomApi) {
    this.roomApi = roomApi;
  }

  public CreateRoomResp create(CreateRoomReq req) {
    BaseResponse<CreateRoomResp> resp = Utils.execute(roomApi.create(req, req));
    CreateRoomResp data = resp.getData();
    data.setLogID(resp.getLogID());
    return data;
  }
}
