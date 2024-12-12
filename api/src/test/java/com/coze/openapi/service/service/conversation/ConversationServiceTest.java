/* (C)2024 */
package com.coze.openapi.service.service.conversation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.ConversationAPI;
import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.connversations.*;
import com.coze.openapi.client.connversations.model.Conversation;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

class ConversationServiceTest {

  @Mock private ConversationAPI conversationAPI;

  @Mock private ConversationMessageAPI messageAPI;

  private ConversationService conversationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    conversationService = new ConversationService(conversationAPI, messageAPI);
  }

  @Test
  void testCreate() throws Exception {
    // 准备测试数据
    CreateConversationReq req = CreateConversationReq.builder().botID("test_bot_id").build();

    Conversation conversation = Conversation.builder().id("conv_id").build();

    BaseResponse<Conversation> baseResponse =
        BaseResponse.<Conversation>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(conversation)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<Conversation>> call = mock(Call.class);
    when(conversationAPI.create(any(CreateConversationReq.class), any(CreateConversationReq.class)))
        .thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    CreateConversationResp result = conversationService.create(req);

    // 验证结果
    assertNotNull(result);
    assertEquals("conv_id", result.getConversation().getId());
  }

  @Test
  void testList() throws Exception {
    // 准备测试数据
    String botId = "test_bot_id";
    ListConversationReq req =
        ListConversationReq.builder().botID(botId).pageNum(1).pageSize(10).build();

    List<Conversation> conversations =
        Arrays.asList(
            Conversation.builder().id("conv1").build(), Conversation.builder().id("conv2").build());

    ListConversationResp listResp =
        ListConversationResp.builder()
            .conversations(conversations)
            .hasMore(false)
            .logID(Utils.TEST_LOG_ID)
            .build();

    BaseResponse<ListConversationResp> baseResponse =
        BaseResponse.<ListConversationResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(listResp)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<ListConversationResp>> call = mock(Call.class);
    when(conversationAPI.list(eq(botId), eq(1), eq(10), any(ListConversationReq.class)))
        .thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    PageResp<Conversation> result = conversationService.list(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(2, result.getItems().size());
    assertEquals("conv1", result.getItems().get(0).getId());
  }

  @Test
  void testRetrieve() throws Exception {
    // 准备测试数据
    String conversationId = "test_conversation_id";
    RetrieveConversationReq req =
        RetrieveConversationReq.builder().conversationID(conversationId).build();

    Conversation conversation = Conversation.builder().id(conversationId).build();

    BaseResponse<Conversation> baseResponse =
        BaseResponse.<Conversation>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(conversation)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<Conversation>> call = mock(Call.class);
    when(conversationAPI.retrieve(eq(conversationId), any(RetrieveConversationReq.class)))
        .thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    RetrieveConversationResp result = conversationService.retrieve(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(conversationId, result.getConversation().getId());
  }

  @Test
  void testClear() throws Exception {
    // 准备测试数据
    String conversationId = "test_conversation_id";
    ClearConversationReq req =
        ClearConversationReq.builder().conversationID(conversationId).build();

    ClearConversationResp clearResp =
        ClearConversationResp.builder().conversationID(conversationId).build();

    BaseResponse<ClearConversationResp> baseResponse =
        BaseResponse.<ClearConversationResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(clearResp)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<ClearConversationResp>> call = mock(Call.class);
    when(conversationAPI.clear(eq(conversationId), any(ClearConversationReq.class)))
        .thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    ClearConversationResp result = conversationService.clear(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(conversationId, result.getConversationID());
  }
}
