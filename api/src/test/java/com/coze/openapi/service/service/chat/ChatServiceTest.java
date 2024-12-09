package com.coze.openapi.service.service.chat;

import com.coze.openapi.api.ChatAPI;
import com.coze.openapi.api.ChatMessageAPI;
import com.coze.openapi.client.chat.*;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.chat.model.ChatStatus;
import com.coze.openapi.client.chat.model.ToolOutput;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ChatServiceTest {

    @Mock
    private ChatAPI chatAPI;

    @Mock
    private ChatMessageAPI chatMessageAPI;

    @Mock
    private Call<BaseResponse<Chat>> chatCall;

    private ChatService chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chatService = new ChatService(chatAPI, chatMessageAPI);
    }

    @Test
    void testCreate() throws Exception {
        // 准备测试数据
        String conversationID = "test_conversation_id";
        String botID = "test_bot_id";
        String userID = "test_user_id";
        CreateChatReq req = CreateChatReq.builder()
                .conversationID(conversationID)
                .botID(botID)
                .userID(userID)
                .build();

        Chat chat = Chat.builder()
                .id("chat_id")
                .conversationID(conversationID)
                .status(ChatStatus.COMPLETED)
                .build();

        BaseResponse<Chat> baseResponse = BaseResponse.<Chat>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(chat)
                .build();

        // 设置 mock 行为
        when(chatAPI.chat(eq(conversationID), any(CreateChatReq.class), any(CreateChatReq.class)))
                .thenReturn(chatCall);
        when(chatCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

        // 执行测试
        CreateChatResp result = chatService.create(req);

        // 验证结果
        assertNotNull(result);
        assertEquals(Utils.TEST_LOG_ID, result.getLogID());
        assertEquals("chat_id", result.getChat().getID());
        assertEquals(ChatStatus.COMPLETED, result.getChat().getStatus());
    }

    @Test
    void testRetrieve() throws Exception {
        // 准备测试数据
        String conversationID = "test_conversation_id";
        String chatID = "test_chat_id";
        RetrieveChatReq req = RetrieveChatReq.builder()
                .conversationID(conversationID)
                .chatID(chatID)
                .build();

        Chat chat = Chat.builder()
                .id(chatID)
                .conversationID(conversationID)
                .status(ChatStatus.COMPLETED)
                .build();

        BaseResponse<Chat> baseResponse = BaseResponse.<Chat>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(chat)
                .build();

        // 设置 mock 行为
        when(chatAPI.retrieve(eq(conversationID), eq(chatID), any(RetrieveChatReq.class)))
                .thenReturn(chatCall);
        when(chatCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

        // 执行测试
        RetrieveChatResp result = chatService.retrieve(req);

        // 验证结果
        assertNotNull(result);
        assertEquals(Utils.TEST_LOG_ID, result.getLogID());
        assertEquals(chatID, result.getChat().getID());
    }

    @Test
    void testCancel() throws Exception {
        // 准备测试数据
        String conversationID = "test_conversation_id";
        String chatID = "test_chat_id";
        CancelChatReq req = CancelChatReq.builder()
                .conversationID(conversationID)
                .chatID(chatID)
                .build();

        Chat chat = Chat.builder()
                .id(chatID)
                .conversationID(conversationID)
                .status(ChatStatus.CANCELLED)
                .build();

        BaseResponse<Chat> baseResponse = BaseResponse.<Chat>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(chat)
                .build();

        // 设置 mock 行为
        when(chatAPI.cancel(any(CancelChatReq.class), any(CancelChatReq.class)))
                .thenReturn(chatCall);
        when(chatCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

        // 执行测试
        CancelChatResp result = chatService.cancel(req);

        // 验证结果
        assertNotNull(result);
        assertEquals(Utils.TEST_LOG_ID, result.getLogID());
        assertEquals(ChatStatus.CANCELLED, result.getChat().getStatus());
    }

    @Test
    void testSubmitToolOutputs() throws Exception {
        // 准备测试数据
        String conversationID = "test_conversation_id";
        String chatID = "test_chat_id";
        SubmitToolOutputsReq req = SubmitToolOutputsReq.builder()
                .conversationID(conversationID)
                .chatID(chatID)
                .toolOutputs(Collections.singletonList(new ToolOutput("mock_id", "mock_output")))
                .build();

        Chat chat = Chat.builder()
                .id(chatID)
                .conversationID(conversationID)
                .status(ChatStatus.COMPLETED)
                .build();

        BaseResponse<Chat> baseResponse = BaseResponse.<Chat>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(chat)
                .build();

        // 设置 mock 行为
        when(chatAPI.submitToolOutputs(eq(conversationID), eq(chatID), 
                any(SubmitToolOutputsReq.class), any(SubmitToolOutputsReq.class)))
                .thenReturn(chatCall);
        when(chatCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

        // 执行测试
        SubmitToolOutputsResp result = chatService.submitToolOutputs(req);

        // 验证结果
        assertNotNull(result);
        assertEquals(Utils.TEST_LOG_ID, result.getLogID());
        assertEquals(chatID, result.getChat().getID());
    }
} 