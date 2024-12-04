package com.coze.openapi.service.service.audio;

import com.coze.openapi.api.AudioRoomAPI;
import com.coze.openapi.client.audio.rooms.CreateRoomReq;
import com.coze.openapi.client.audio.rooms.CreateRoomResult;
import com.coze.openapi.service.utils.Utils;

public class RoomService {
    private final AudioRoomAPI roomApi;

    public RoomService(AudioRoomAPI roomApi) {
        this.roomApi = roomApi;
    }

    public CreateRoomResult create(CreateRoomReq req) {
        return Utils.execute(roomApi.create(req)).getData();
    }
}
