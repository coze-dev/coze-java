package com.coze.openapi.service.service.common;

import com.coze.openapi.client.exception.CozeApiExcetion;
import com.coze.openapi.client.exception.CozeError;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.utils.Utils;
import io.reactivex.FlowableEmitter;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AbstractEventCallbackTest {

    @Mock
    private FlowableEmitter<String> emitter;

    @Mock
    private Call<ResponseBody> call;

    private TestEventCallback callback;

    private static class TestEventCallback extends AbstractEventCallback<String> {
        public TestEventCallback(FlowableEmitter<String> emitter) {
            super(emitter);
        }

        @Override
        protected boolean processLine(String line, BufferedReader reader, String logID) {
            emitter.onNext(line);
            return line.contains("done");
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        callback = new TestEventCallback(emitter);
        when(emitter.isCancelled()).thenReturn(false);
    }

    @Test
    void testSuccessfulStreamResponse() throws IOException {
        // 准备测试数据
        String testData = "test line 1\ntest line 2\n done\n";
        ResponseBody responseBody = ResponseBody.create(
                testData,
                MediaType.parse("text/event-stream")
        );
        Response<ResponseBody> response = Response.success(responseBody, Utils.getCommonHeader());

        // 执行测试
        callback.onResponse(call, response);

        // 验证结果
        verify(emitter, times(3)).onNext(anyString());
        verify(emitter).onNext("test line 1");
        verify(emitter).onNext("test line 2");
        verify(emitter).onComplete();
    }

    @Test
    void testErrorResponse() throws IOException {
        // 准备错误响应数据
        CozeError error = new CozeError();
        error.setErrorMessage("Test error");
        String errorJson = "{\"error_message\":\"Test error\"}";
        ResponseBody errorBody = ResponseBody.create(
                errorJson,
                MediaType.parse("application/json")
        );
        Response<ResponseBody> response = Response.error(400, errorBody);

        // 执行测试
        callback.onResponse(call, response);

        // 验证结果
        verify(emitter).onError(any(CozeApiExcetion.class));
    }

    @Test
    void testJsonResponse() throws IOException {
        // 准备 JSON 响应数据
        BaseResponse<String> baseResponse = BaseResponse.<String>builder()
                .code(0)
                .msg("success")
                .data("test data")
                .build();
        String jsonResponse = "{\"code\":0,\"msg\":\"success\",\"data\":\"test data\"}";
        ResponseBody responseBody = ResponseBody.create(
                jsonResponse,
                MediaType.parse("application/json")
        );
        Headers headers = Headers.of(
            "Content-Type", "application/json",
            Utils.LOG_HEADER, "test_log_id"
        );
        Response<ResponseBody> response = Response.success(responseBody, headers);

        // 执行测试
        callback.onResponse(call, response);

        // 验证结果
        verify(emitter, never()).onNext(anyString());
        verify(emitter, never()).onError(any());
    }

    @Test
    void testErrorJsonResponse() throws IOException {
        // 准备错误的 JSON 响应数据
        String jsonResponse = "{\"code\":1001,\"msg\":\"error message\"}";
        ResponseBody responseBody = ResponseBody.create(
                jsonResponse,
                MediaType.parse("application/json")
        );
        Headers headers = Headers.of(
            "Content-Type", "application/json",
            Utils.LOG_HEADER, "test_log_id"
        );
        Response<ResponseBody> response = Response.success(responseBody, headers);

        // 执行测试
        callback.onResponse(call, response);

        // 验证结果
        verify(emitter).onError(any(CozeApiExcetion.class));
    }

    @Test
    void testOnFailure() {
        // 准备测试数据
        Throwable error = new IOException("Network error");

        // 执行测试
        callback.onFailure(call, error);

        // 验证结果
        verify(emitter).onError(error);
    }

    @Test
    void testCancelledEmitter() throws IOException {
        // 设置 emitter 为已取消状态
        when(emitter.isCancelled()).thenReturn(true);

        // 准备测试数据
        String testData = "test line\n";
        ResponseBody responseBody = ResponseBody.create(
                testData,
                MediaType.parse("text/event-stream")
        );
        Response<ResponseBody> response = Response.success(responseBody);

        // 执行测试
        callback.onResponse(call, response);

        // 验证结果
        verify(emitter, never()).onNext(anyString());
        verify(emitter).onComplete();
    }
} 