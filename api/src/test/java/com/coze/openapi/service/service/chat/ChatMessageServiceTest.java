/* (C)2024 */
package com.coze.openapi.service.service.chat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.ChatMessageAPI;
import com.coze.openapi.client.chat.message.ListMessageReq;
import com.coze.openapi.client.chat.message.ListMessageResp;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

class ChatMessageServiceTest {

  @Mock private ChatMessageAPI chatMessageAPI;

  @Mock private Call<BaseResponse<List<Message>>> listCall;

  private ChatMessageService chatMessageService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    chatMessageService = new ChatMessageService(chatMessageAPI);
  }

  @Test
  void testList() throws Exception {
    // 准备测试数据
    String conversationID = "test_conversation_id";
    String chatID = "test_chat_id";
    ListMessageReq req =
        ListMessageReq.builder().conversationID(conversationID).chatID(chatID).build();

    List<Message> messages =
        Arrays.asList(
            Message.builder().id("msg1").content("Hello").build(),
            Message.builder().id("msg2").content("World").build());

    BaseResponse<List<Message>> baseResponse =
        BaseResponse.<List<Message>>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(messages)
            .build();

    // 设置 mock 行为
    when(chatMessageAPI.list(eq(conversationID), eq(chatID), any(ListMessageReq.class)))
        .thenReturn(listCall);
    when(listCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    ListMessageResp result = chatMessageService.list(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    assertEquals(2, result.getMessages().size());
    assertEquals("msg1", result.getMessages().get(0).getId());
    assertEquals("Hello", result.getMessages().get(0).getContent());
  }
}
