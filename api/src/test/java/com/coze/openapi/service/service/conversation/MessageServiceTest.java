package com.coze.openapi.service.service.conversation;

import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.connversations.message.*;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.client.connversations.message.model.MessageRole;
import com.coze.openapi.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageServiceTest {

    @Mock
    private ConversationMessageAPI messageAPI;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(messageAPI);
    }

    @Test
    void testCreate() throws Exception {
        // 准���测试数据
        String conversationId = "test_conversation_id";
        CreateMessageReq req = CreateMessageReq.builder()
                .conversationID(conversationId)
                .role(MessageRole.USER)
                .contentType(MessageContentType.TEXT)
                .content("Test message")
                .build();

        Message message = Message.buildUserQuestionText("Test message");
        message.setId("msg_id");

        BaseResponse<Message> baseResponse = BaseResponse.<Message>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(message)
                .build();

        // 创建 mock Call 对象
        Call<BaseResponse<Message>> call = mock(Call.class);
        when(messageAPI.create(eq(conversationId), any(CreateMessageReq.class), any(CreateMessageReq.class)))
                .thenReturn(call);
        when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

        // 执行测试
        CreateMessageResp result = messageService.create(req);

        // 验证结果
        assertNotNull(result);
        assertEquals("msg_id", result.getMessage().getId());
        assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    }

    @Test
    void testList() throws Exception {
        // 准备测试数据
        String conversationId = "test_conversation_id";
        ListMessageReq req = ListMessageReq.builder()
                .conversationID(conversationId)
                .limit(10)
                .build();

        List<Message> messages = Arrays.asList(
                Message.builder().id("msg1").content("Message 1").build(),
                Message.builder().id("msg2").content("Message 2").build()
        );

        ListMessageResp listResp = ListMessageResp.builder()
                .data(messages)
                .firstID("msg1")
                .lastID("msg2")
                .build();

        BaseResponse<ListMessageResp> baseResponse = BaseResponse.<ListMessageResp>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(listResp)
                .build();

        // 创建 mock Call 对象
        Call<ListMessageResp> call = mock(Call.class);
        when(messageAPI.list(eq(conversationId), any(ListMessageReq.class), any(ListMessageReq.class)))
                .thenReturn(call);
        when(call.execute()).thenReturn(Response.success(baseResponse.getData(), Utils.getCommonHeader()));

        // 执行测试
        PageResp<Message> result = messageService.list(req);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        assertEquals("msg1", result.getItems().get(0).getId());
    }

    @Test
    void testRetrieve() throws Exception {
        // 准备测试数据
        String conversationId = "test_conversation_id";
        String messageId = "test_message_id";
        RetrieveMessageReq req = RetrieveMessageReq.builder()
                .conversationID(conversationId)
                .messageID(messageId)
                .build();

        Message message = Message.builder()
                .id(messageId)
                .content("Test message")
                .build();

        BaseResponse<Message> baseResponse = BaseResponse.<Message>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(message)
                .build();

        // 创建 mock Call 对象
        Call<BaseResponse<Message>> call = mock(Call.class);
        when(messageAPI.retrieve(eq(conversationId), eq(messageId), any(RetrieveMessageReq.class)))
                .thenReturn(call);
        when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

        // 执行测试
        RetrieveMessageResp result = messageService.retrieve(req);

        // 验证结果
        assertNotNull(result);
        assertEquals(messageId, result.getMessage().getId());
    }

    @Test
    void testDelete() throws Exception {
        // 准备测试数据
        String conversationId = "test_conversation_id";
        String messageId = "test_message_id";
        DeleteMessageReq req = DeleteMessageReq.builder()
                .conversationID(conversationId)
                .messageID(messageId)
                .build();

        Message message = Message.builder()
                .id(messageId)
                .build();

        BaseResponse<Message> baseResponse = BaseResponse.<Message>builder()
                .code(0)
                .msg("success")
                .logID(Utils.TEST_LOG_ID)
                .data(message)
                .build();

        // 创建 mock Call 对象
        Call<BaseResponse<Message>> call = mock(Call.class);
        when(messageAPI.delete(eq(conversationId), eq(messageId), any(DeleteMessageReq.class)))
                .thenReturn(call);
        when(call.execute()).thenReturn(Response.success(baseResponse));

        // 执行测试
        DeleteMessageResp result = messageService.delete(req);

        // 验证结果
        assertNotNull(result);
        assertEquals(messageId, result.getMessage().getId());
    }
} 