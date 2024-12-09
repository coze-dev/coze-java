package com.coze.openapi.service.service.file;

import com.coze.openapi.api.FileAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.files.RetrieveFileReq;
import com.coze.openapi.client.files.RetrieveFileResp;
import com.coze.openapi.client.files.UploadFileReq;
import com.coze.openapi.client.files.UploadFileResp;
import com.coze.openapi.client.files.model.FileInfo;
import com.coze.openapi.utils.Utils;
import okhttp3.MultipartBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileServiceTest {

    @Mock
    private FileAPI fileAPI;

    private FileService fileService;

    private File testFile;
    private FileInfo mockFileInfo;
    private BaseResponse<FileInfo> mockBaseResponse;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        fileService = new FileService(fileAPI);

        // 创建测试文件
        testFile = File.createTempFile("test", ".txt");

        // 准备通用的 mock 响应数据
        mockFileInfo = FileInfo.builder()
                .id("file_id")
                .fileName("test.txt")
                .bytes(1024)
                .build();

        mockBaseResponse = BaseResponse.<FileInfo>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(mockFileInfo)
                .build();
    }

    @Test
    void testUploadWithFilePath() throws Exception {
        // 准备测试数据
        UploadFileReq req = UploadFileReq.builder()
                .filePath(testFile.getAbsolutePath())
                .build();

        // 创建 mock Call 对象
        Call<BaseResponse<FileInfo>> call = mock(Call.class);
        when(fileAPI.upload(any(MultipartBody.Part.class), any(UploadFileReq.class)))
                .thenReturn(call);
        when(call.execute()).thenReturn(Response.success(mockBaseResponse, Utils.getCommonHeader()));

        // 执行测试
        UploadFileResp result = fileService.upload(req);

        // 验证结果
        assertNotNull(result);
        assertEquals("file_id", result.getFileInfo().getID());
        assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    }

    @Test
    void testUploadWithFileBytes() throws Exception {
        // 准备测试数据
        byte[] fileBytes = "Test content".getBytes();
        UploadFileReq req = UploadFileReq.builder()
                .fileBytes(fileBytes)
                .fileName("test.txt")
                .build();

        // 创建 mock Call 对象
        Call<BaseResponse<FileInfo>> call = mock(Call.class);
        when(fileAPI.upload(any(MultipartBody.Part.class), any(UploadFileReq.class)))
                .thenReturn(call);
        when(call.execute()).thenReturn(Response.success(mockBaseResponse, Utils.getCommonHeader()));

        // 执行测试
        UploadFileResp result = fileService.upload(req);

        // 验证结果
        assertNotNull(result);
        assertEquals("file_id", result.getFileInfo().getID());
        assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    }

    @Test
    void testUploadWithFile() throws Exception {
        // 准备测试数据
        UploadFileReq req = UploadFileReq.builder()
                .file(testFile)
                .fileName("test.txt")
                .build();

        // 创建 mock Call 对象
        Call<BaseResponse<FileInfo>> call = mock(Call.class);
        when(fileAPI.upload(any(MultipartBody.Part.class), any(UploadFileReq.class)))
                .thenReturn(call);
        when(call.execute()).thenReturn(Response.success(mockBaseResponse, Utils.getCommonHeader()));

        // 执行测试
        UploadFileResp result = fileService.upload(req);

        // 验证结果
        assertNotNull(result);
        assertEquals("file_id", result.getFileInfo().getID());
        assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    }

    @Test
    void testUploadWithInvalidRequest() {
        // 测试空请求
        UploadFileReq emptyReq = UploadFileReq.builder().build();
        assertThrows(IllegalArgumentException.class, () -> fileService.upload(emptyReq));
    }

    @Test
    void testRetrieve() throws Exception {
        // 准备测试数据
        String fileId = "test_file_id";
        RetrieveFileReq req = RetrieveFileReq.builder()
                .fileID(fileId)
                .build();

        // 创建 mock Call 对象
        Call<BaseResponse<FileInfo>> call = mock(Call.class);
        when(fileAPI.retrieve(eq(fileId), any(RetrieveFileReq.class)))
                .thenReturn(call);
        when(call.execute()).thenReturn(Response.success(mockBaseResponse, Utils.getCommonHeader()));

        // 执行测试
        RetrieveFileResp result = fileService.retrieve(req);

        // 验证结果
        assertNotNull(result);
        assertEquals("file_id", result.getFileInfo().getID());
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