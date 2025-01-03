package com.coze.openapi.service.service.audio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.AudioRoomAPI;
import com.coze.openapi.client.audio.rooms.CreateRoomReq;
import com.coze.openapi.client.audio.rooms.CreateRoomResp;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

class RoomServiceTest {

  @Mock private AudioRoomAPI audioRoomAPI;

  @Mock private Call<BaseResponse<CreateRoomResp>> createCall;

  private RoomService roomService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    roomService = new RoomService(audioRoomAPI);
  }

  @Test
  void testCreate() throws Exception {
    // 准备测试数据
    CreateRoomReq req = CreateRoomReq.builder().botID("mock bot id").build();

    CreateRoomResp createResp = CreateRoomResp.builder().roomID("room_id").build();

    BaseResponse<CreateRoomResp> baseResponse =
        BaseResponse.<CreateRoomResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(createResp)
            .build();

    // 设置 mock 行为
    when(audioRoomAPI.create(any(CreateRoomReq.class), any(CreateRoomReq.class)))
        .thenReturn(createCall);
    when(createCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    CreateRoomResp result = roomService.create(req);

    // 验证结果
    assertNotNull(result);
    assertEquals("room_id", result.getRoomID());
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }
}
