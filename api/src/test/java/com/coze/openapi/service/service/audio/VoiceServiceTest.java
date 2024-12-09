package com.coze.openapi.service.service.audio;

import com.coze.openapi.api.AudioVoiceAPI;
import com.coze.openapi.client.audio.common.AudioFormat;
import com.coze.openapi.client.audio.voices.*;
import com.coze.openapi.client.audio.voices.model.Voice;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.utils.Utils;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VoiceServiceTest {

    @Mock
    private AudioVoiceAPI audioVoiceAPI;

    @Mock
    private Call<BaseResponse<CloneVoiceResp>> cloneCall;

    @Mock
    private Call<BaseResponse<ListVoiceResp>> listCall;

    private VoiceService voiceService;

    private static String mockFilePath;
    private static File testFile;


    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        voiceService = new VoiceService(audioVoiceAPI);
        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(true);

        // 创建临时测试文件
        testFile = File.createTempFile("test", ".mp3");
        mockFilePath = testFile.getAbsolutePath();
    }

    @Test
    void testClone() throws Exception {
        // 准备测试数据
        CloneVoiceReq req = CloneVoiceReq.builder()
                .voiceName("test_voice")
                .audioFormat(AudioFormat.MP3)
                .filePath(mockFilePath)
                .build();

        CloneVoiceResp cloneResp = CloneVoiceResp.builder()
                .voiceID("voice_id")
                .build();

        BaseResponse<CloneVoiceResp> baseResponse = BaseResponse.<CloneVoiceResp>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(cloneResp)
                .build();

        // 设置 mock 行为
        when(audioVoiceAPI.clone(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(cloneCall);
        when(cloneCall.execute()).thenReturn(Response.success(baseResponse));

        // 执行测试
        CloneVoiceResp result = voiceService.clone(req);

        // 验证结果
        assertNotNull(result);
        assertEquals("voice_id", result.getVoiceID());
    }

    @Test
    void testList() throws Exception {
        // 准备测试数���
        ListVoiceReq req = ListVoiceReq.builder()
                .pageNum(1)
                .pageSize(10)
                .filterSystemVoice(false)
                .build();

        List<Voice> voices = Arrays.asList(
                Voice.builder().voiceID("voice1").name("Voice 1").build(),
                Voice.builder().voiceID("voice2").name("Voice 2").build()
        );

        ListVoiceResp listResp = ListVoiceResp.builder()
                .voiceList(voices)
                .logID(Utils.TEST_LOG_ID)
                .build();

        BaseResponse<ListVoiceResp> baseResponse = BaseResponse.<ListVoiceResp>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(listResp)
                .build();

        // 设置 mock 行为
        when(audioVoiceAPI.list(eq(false), eq(1), eq(10), any(ListVoiceReq.class)))
                .thenReturn(listCall);
        when(listCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

        // 执行测试
        PageResp<Voice> result = voiceService.list(req);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        assertEquals("voice1", result.getItems().get(0).getVoiceID());
        assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    }

    @AfterEach
    void tearDown() {
        // 清理临时文件
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }
} 