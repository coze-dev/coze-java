/* (C)2024 */
package com.coze.openapi.service.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.common.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response.Builder;
import retrofit2.Call;
import retrofit2.Response;

public class UtilsTest {

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    objectMapper = Utils.defaultObjectMapper();
  }

  public static class TestResponse extends BaseResp {}

  @Test
  @SuppressWarnings("unchecked")
  public void testExecuteWithBaseResponse() throws IOException {
    // 准备测试数据
    String logId = "test-log-id";
    BaseResponse<BaseResp> baseResponse = new BaseResponse<BaseResp>();
    BaseResp baseResp = new TestResponse();
    baseResponse.setData(baseResp);

    // 创建模拟对象
    Call<BaseResponse<BaseResp>> call = mock(Call.class);
    okhttp3.Response rawResponse =
        new Builder()
            .request(new Request.Builder().url("http://test.com").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .headers(Headers.of(Utils.LOG_HEADER, logId))
            .build();

    Response<BaseResponse<BaseResp>> response = Response.success(baseResponse, rawResponse);
    when(call.execute()).thenReturn(response);

    // 执行测试
    BaseResponse<BaseResp> result = Utils.execute(call);

    // 验证结果
    assertNotNull(result);
    assertEquals(logId, result.getLogID());
    assertNotNull(result.getData());
    assertEquals(logId, ((BaseResp) result.getData()).getLogID());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testExecuteWithBaseResp() throws IOException {
    // 准备测试数据
    String logId = "test-log-id";
    BaseResp baseResp = new TestResponse();

    // 创建模拟对象
    Call<BaseResp> call = mock(Call.class);
    okhttp3.Response rawResponse =
        new Builder()
            .request(new Request.Builder().url("http://test.com").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .headers(Headers.of(Utils.LOG_HEADER, logId))
            .build();

    Response<BaseResp> response = Response.success(baseResp, rawResponse);
    when(call.execute()).thenReturn(response);

    // 执行测试
    BaseResp result = Utils.execute(call);

    // 验证结果
    assertNotNull(result);
    assertEquals(logId, result.getLogID());
  }

  @Test
  public void testExecuteWithHttpError() throws IOException {
    // 创建模拟对象
    Call<BaseResp> call = mock(Call.class);
    okhttp3.Response rawResponse =
        new Builder()
            .request(new Request.Builder().url("http://test.com").build())
            .protocol(Protocol.HTTP_1_1)
            .code(400)
            .message("Bad Request")
            .build();

    Response<BaseResp> response = Response.error(400, okhttp3.ResponseBody.create("", null));
    when(call.execute()).thenReturn(response);

    // 执行测试，应该抛出异常
    assertThrowsExactly(RuntimeException.class, () -> Utils.execute(call));
  }

  @Test
  public void testJsonConversion() {
    // 准备测试数据
    TestObject testObj = new TestObject("test", 123);

    // 测试对象转JSON
    String json = Utils.toJson(testObj);
    assertNotNull(json);
    assertTrue(json.contains("test"));
    assertTrue(json.contains("123"));

    // 测试JSON转对象
    TestObject parsedObj = Utils.fromJson(json, TestObject.class);
    assertNotNull(parsedObj);
    assertEquals("test", parsedObj.getName());
    assertEquals(123, parsedObj.getValue());
  }

  @Test
  public void testGenRandomSign() {
    // 测试生成16位随机字符串
    String sign = Utils.genRandomSign(16);
    assertNotNull(sign);
    assertEquals(16, sign.length());
    assertTrue(sign.matches("[0-9a-f]+"));

    // 测试生成32位随机字符串
    sign = Utils.genRandomSign(32);
    assertNotNull(sign);
    assertEquals(32, sign.length());
    assertTrue(sign.matches("[0-9a-f]+"));
  }

  // 用于测试JSON转换的内部类
  private static class TestObject {
    private String name;
    private int value;

    public TestObject() {}

    public TestObject(String name, int value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }
  }
}
