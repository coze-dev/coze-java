package com.coze.openapi.service.service.common;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.exception.CozeApiException;
import com.coze.openapi.client.exception.CozeError;
import com.coze.openapi.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.FlowableEmitter;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractChatEventCallbackTest {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final int ASYNC_TIMEOUT_SECONDS = 20;
    
    @Mock private FlowableEmitter<String> emitter;
    @Mock private Call<ResponseBody> call;
    private TestEventCallback callback;
    private CountDownLatch latch;

    private static class TestEventCallback extends AbstractEventCallback<String> {
        private final CountDownLatch latch;

        public TestEventCallback(FlowableEmitter<String> emitter, CountDownLatch latch) {
            super(emitter);
            this.latch = latch;
        }

        @Override
        protected boolean processLine(String line, BufferedReader reader, String logID) {
            emitter.onNext(line);
            boolean isDone = line.contains("done");
            if (isDone) {
                latch.countDown();
            }
            return isDone;
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            try {
                super.onFailure(call, t);
            } finally {
                latch.countDown();
            }
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        latch = new CountDownLatch(1);
        callback = new TestEventCallback(emitter, latch);
        when(emitter.isCancelled()).thenReturn(Boolean.valueOf(false));
    }

    @Test
    void testSuccessfulStreamResponse() throws IOException, InterruptedException {
        // 准备测试数据
        String testData = "test line 1\ntest line 2\n done\n";
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("text/event-stream"), testData);
        Response<ResponseBody> response = Response.success(responseBody, Utils.getCommonHeader());

        // 执行测试
        callback.onResponse(call, response);

        // 等待异步操作完成
        assertTrue(latch.await(ASYNC_TIMEOUT_SECONDS, TimeUnit.SECONDS));

        // 验证结果
        verify(emitter, times(3)).onNext(anyString());
        verify(emitter).onNext("test line 1");
        verify(emitter).onNext("test line 2");
        verify(emitter).onNext(" done");
        verify(emitter).onComplete();
    }

    @Test
    void testErrorResponse() throws IOException, InterruptedException {
        // 准备错误响应数据
        CozeError error = new CozeError("Test error", "error_code", "test_request_id");
        String errorJson = mapper.writeValueAsString(error);
        ResponseBody errorBody = ResponseBody.create(MediaType.parse("application/json"), errorJson);
        Response<ResponseBody> response = Response.error(400, errorBody);
        // 设置验证
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(emitter).onError(any(CozeApiException.class));
        // 执行测试
        callback.onResponse(call, response);

        // 等待异步操作完成
        assertTrue(latch.await(ASYNC_TIMEOUT_SECONDS, TimeUnit.SECONDS));

        // 验证结果
        verify(emitter).onError(any(CozeApiException.class));
    }

    @Test
    void testJsonResponse() throws IOException, InterruptedException {
        // 准备 JSON 响应数据
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setCode(Integer.valueOf(0));
        baseResponse.setMsg("success");
        baseResponse.setData("test data");
        
        String jsonResponse = mapper.writeValueAsString(baseResponse);
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("application/json"), jsonResponse);
        Headers headers = Headers.of("Content-Type", "application/json", Utils.LOG_HEADER, "test_log_id");
        Response<ResponseBody> response = Response.success(responseBody, headers);

        // 设置 onComplete 时触发 latch
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(emitter).onComplete();

        // 执行测试
        callback.onResponse(call, response);

        // 等待异步操作完成
        assertTrue(latch.await(ASYNC_TIMEOUT_SECONDS, TimeUnit.SECONDS));

        // 验证结果
        verify(emitter, never()).onNext(anyString());
        verify(emitter, never()).onError(any());
        verify(emitter).onComplete();
    }

    @Test
    void testErrorJsonResponse() throws InterruptedException {
        // 准备错误的 JSON 响应数据
        String jsonResponse = "{\"code\":1001,\"msg\":\"error message\"}";
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("application/json"), jsonResponse);
        Headers headers = Headers.of("Content-Type", "application/json", Utils.LOG_HEADER, "test_log_id");
        Response<ResponseBody> response = Response.success(responseBody, headers);

        // 设置验证
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(emitter).onError(any(CozeApiException.class));

        // 执行测试
        callback.onResponse(call, response);

        // 等待异步操作完成
        assertTrue(latch.await(ASYNC_TIMEOUT_SECONDS, TimeUnit.SECONDS));

        // 验证结果
        verify(emitter).onError(any(CozeApiException.class));
    }

    @Test
    void testOnFailure() throws InterruptedException {
        // 准备测试数据
        Throwable error = new IOException("Network error");

        // 执行测试
        callback.onFailure(call, error);

        // 等待异步操作完成
        assertTrue(latch.await(ASYNC_TIMEOUT_SECONDS, TimeUnit.SECONDS));

        // 验证结果
        verify(emitter).onError(error);
    }

    @Test
    void testCancelledEmitter() throws InterruptedException {
        // 设置 emitter 为已取消状态
        when(emitter.isCancelled()).thenReturn(Boolean.valueOf(true));

        // 准备测试数据
        String testData = "test line\n";
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("text/event-stream"), testData);
        Response<ResponseBody> response = Response.success(responseBody);
        
        // 设置 onComplete 时触发 latch
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(emitter).onComplete();

        // 执行测试
        callback.onResponse(call, response);

        // 等待异步操作完成
        assertTrue(latch.await(ASYNC_TIMEOUT_SECONDS, TimeUnit.SECONDS));

        // 验证结果
        verify(emitter, never()).onNext(anyString());
        verify(emitter, never()).onError(any());
        verify(emitter).onComplete();
    }
}
