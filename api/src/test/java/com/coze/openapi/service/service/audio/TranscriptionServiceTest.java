package com.coze.openapi.service.service.audio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.AudioTranscriptionAPI;
import com.coze.openapi.client.audio.transcriptions.CreateTranscriptionsReq;
import com.coze.openapi.client.audio.transcriptions.CreateTranscriptionsResp;
import com.coze.openapi.client.common.BaseResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;

class TranscriptionServiceTest {

  @Mock private AudioTranscriptionAPI transcriptionAPI;
  @Mock private Call<BaseResponse<CreateTranscriptionsResp>> mockCall;

  private TranscriptionService transcriptionService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    transcriptionService = new TranscriptionService(transcriptionAPI);
  }

  @Test
  void testCreateWithFilePath(@TempDir File tempDir) throws Exception {
    // 准备测试文件
    File testFile = new File(tempDir, "test-audio.wav");
    Files.write(testFile.toPath(), "test audio content".getBytes());

    // 准备请求和预期响应
    CreateTranscriptionsReq req =
        CreateTranscriptionsReq.builder().filePath(testFile.getAbsolutePath()).build();

    CreateTranscriptionsResp expectedResp =
        CreateTranscriptionsResp.builder().text("测试转录文本").build();

    BaseResponse<CreateTranscriptionsResp> baseResponse = new BaseResponse<>();
    baseResponse.setData(expectedResp);

    // 配置 mock
    when(transcriptionAPI.create(any(MultipartBody.Part.class), eq(req))).thenReturn(mockCall);
    when(mockCall.execute()).thenReturn(Response.success(baseResponse));

    // 执行测试
    CreateTranscriptionsResp resp = transcriptionService.create(req);

    // 验证结果
    assertEquals("测试转录文本", resp.getText());
    verify(transcriptionAPI).create(any(MultipartBody.Part.class), eq(req));
  }

  @Test
  void testCreateWithFileBytes() throws Exception {
    // 准备测试数据
    byte[] fileBytes = "test audio content".getBytes();
    String fileName = "test-audio.wav";

    CreateTranscriptionsReq req =
        CreateTranscriptionsReq.builder().fileBytes(fileBytes).fileName(fileName).build();

    CreateTranscriptionsResp expectedResp =
        CreateTranscriptionsResp.builder().text("测试转录文本").build();

    BaseResponse<CreateTranscriptionsResp> baseResponse = new BaseResponse<>();
    baseResponse.setData(expectedResp);

    // 配置 mock
    when(transcriptionAPI.create(any(MultipartBody.Part.class), eq(req))).thenReturn(mockCall);
    when(mockCall.execute()).thenReturn(Response.success(baseResponse));

    // 执行测试
    CreateTranscriptionsResp resp = transcriptionService.create(req);

    // 验证结果
    assertEquals("测试转录文本", resp.getText());
    verify(transcriptionAPI).create(any(MultipartBody.Part.class), eq(req));
  }

  @Test
  void testCreateWithFile(@TempDir File tempDir) throws Exception {
    // 准备测试文件
    File testFile = new File(tempDir, "test-audio.wav");
    Files.write(testFile.toPath(), "test audio content".getBytes());

    CreateTranscriptionsReq req =
        CreateTranscriptionsReq.builder().file(testFile).fileName(testFile.getName()).build();

    CreateTranscriptionsResp expectedResp =
        CreateTranscriptionsResp.builder().text("测试转录文本").build();

    BaseResponse<CreateTranscriptionsResp> baseResponse = new BaseResponse<>();
    baseResponse.setData(expectedResp);

    // 配置 mock
    when(transcriptionAPI.create(any(MultipartBody.Part.class), eq(req))).thenReturn(mockCall);
    when(mockCall.execute()).thenReturn(Response.success(baseResponse));

    // 执行测试
    CreateTranscriptionsResp resp = transcriptionService.create(req);

    // 验证结果
    assertEquals("测试转录文本", resp.getText());
    verify(transcriptionAPI).create(any(MultipartBody.Part.class), eq(req));
  }

  @Test
  void testCreateWithNoFileSource() {
    CreateTranscriptionsReq req = CreateTranscriptionsReq.builder().build();

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> transcriptionService.create(req));

    assertEquals("file source is required", exception.getMessage());
  }
}
