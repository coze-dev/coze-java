/* (C)2024 */
package com.coze.openapi.service.service.audio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.AudioSpeechAPI;
import com.coze.openapi.client.audio.speech.CreateSpeechReq;
import com.coze.openapi.client.audio.speech.CreateSpeechResp;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

class SpeechServiceTest {

  @Mock private AudioSpeechAPI audioSpeechAPI;

  @Mock private Call<ResponseBody> createCall;

  private SpeechService speechService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    speechService = new SpeechService(audioSpeechAPI);
  }

  @Test
  void testCreate() throws Exception {
    // 准备测试数据
    CreateSpeechReq req =
        CreateSpeechReq.builder().input("Test speech").voiceID("mock voice id").build();

    ResponseBody responseBody =
        ResponseBody.create(new byte[] {1, 2, 3, 4}, MediaType.parse("audio/mpeg"));

    // 设置 mock 行为
    when(audioSpeechAPI.create(any(CreateSpeechReq.class), any(CreateSpeechReq.class)))
        .thenReturn(createCall);
    when(createCall.execute()).thenReturn(Response.success(responseBody));

    // 执行测试
    CreateSpeechResp result = speechService.create(req);

    // 验证结果
    assertNotNull(result);
    assertNotNull(result.getResponse());
  }

  @Test
  void testCreateWithError() throws Exception {
    // 准备测试数据
    CreateSpeechReq req =
        CreateSpeechReq.builder().input("Test speech").voiceID("mock voice id").build();

    // 设置 mock 行为
    when(audioSpeechAPI.create(any(CreateSpeechReq.class), any(CreateSpeechReq.class)))
        .thenReturn(createCall);
    when(createCall.execute()).thenThrow(new RuntimeException("Network error"));

    // 验证异常抛出
    assertThrows(RuntimeException.class, () -> speechService.create(req));
  }
}
