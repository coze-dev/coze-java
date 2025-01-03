package com.coze.openapi.service.service.bots;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.BotAPI;
import com.coze.openapi.client.bots.*;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.bots.model.SimpleBot;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

class BotServiceTest {

  @Mock private BotAPI botAPI;

  @Mock private Call<BaseResponse<ListBotResp>> listCall;

  @Mock private Call<BaseResponse<Bot>> retrieveCall;

  @Mock private Call<BaseResponse<CreateBotResp>> createCall;

  @Mock private Call<BaseResponse<Void>> updateCall;

  @Mock private Call<BaseResponse<PublishBotResp>> publishCall;

  private BotService botService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    botService = new BotService(botAPI);
  }

  @Test
  public void testList() throws Exception {
    // 准备测试数据
    String spaceId = "test_space_id";
    ListBotReq req = ListBotReq.builder().spaceID(spaceId).pageNum(1).pageSize(20).build();

    // 模拟返回数据
    List<SimpleBot> bots =
        Arrays.asList(
            SimpleBot.builder().botID("bot1").botName("Bot 1").build(),
            SimpleBot.builder().botID("bot2").botName("Bot 2").build());

    ListBotResp listBotResp = ListBotResp.builder().total(2).bots(bots).build();

    BaseResponse<ListBotResp> baseResponse =
        BaseResponse.<ListBotResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(listBotResp)
            .build();

    // 设置 mock 行为
    when(botAPI.list(eq(spaceId), eq(1), eq(20), any(ListBotReq.class))).thenReturn(listCall);
    when(listCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    PageResp<SimpleBot> result = botService.list(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(2, result.getTotal());
    assertEquals(2, result.getItems().size());
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    assertEquals("bot1", result.getItems().get(0).getBotID());
  }

  @Test
  void testRetrieve() throws Exception {
    // 准备测试数据
    String botId = "test_bot_id";
    RetrieveBotReq req = RetrieveBotReq.builder().botID(botId).build();

    Bot bot = Bot.builder().botID(botId).name("Test Bot").build();

    BaseResponse<Bot> baseResponse =
        BaseResponse.<Bot>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(bot)
            .build();

    // 设置 mock 行为
    when(botAPI.retrieve(eq(botId), any(RetrieveBotReq.class))).thenReturn(retrieveCall);
    when(retrieveCall.execute())
        .thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    RetrieveBotResp result = botService.retrieve(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    assertEquals(botId, result.getBot().getBotID());
  }

  @Test
  void testCreate() throws Exception {
    // 准备测试数据
    CreateBotReq req = CreateBotReq.builder().name("New Bot").spaceID("mock_space_id").build();

    CreateBotResp createResp = CreateBotResp.builder().botID("new_bot_id").build();

    BaseResponse<CreateBotResp> baseResponse =
        BaseResponse.<CreateBotResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(createResp)
            .build();

    // 设置 mock 行为
    when(botAPI.create(any(CreateBotReq.class), any(CreateBotReq.class))).thenReturn(createCall);
    when(createCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    CreateBotResp result = botService.create(req);

    // 验证结果
    assertNotNull(result);
    assertEquals("new_bot_id", result.getBotID());
  }

  @Test
  void testUpdate() throws Exception {
    // 准备测试数据
    UpdateBotReq req = UpdateBotReq.builder().botID("bot_id").name("Updated Bot").build();

    BaseResponse<Void> baseResponse =
        BaseResponse.<Void>builder().code(0).msg("success").logID(Utils.TEST_LOG_ID).build();

    // 设置 mock 行为
    when(botAPI.update(any(UpdateBotReq.class), any(UpdateBotReq.class))).thenReturn(updateCall);
    when(updateCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    UpdateBotResp result = botService.update(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }

  @Test
  void testPublish() throws Exception {
    // 准备测试数据
    PublishBotReq req = PublishBotReq.builder().botID("bot_id").build();

    PublishBotResp publishResp = PublishBotResp.builder().botID("bot_id").build();

    BaseResponse<PublishBotResp> baseResponse =
        BaseResponse.<PublishBotResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(publishResp)
            .build();

    // 设置 mock 行为
    when(botAPI.publish(any(PublishBotReq.class), any(PublishBotReq.class)))
        .thenReturn(publishCall);
    when(publishCall.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    PublishBotResp result = botService.publish(req);

    // 验证结果
    assertNotNull(result);
    assertEquals("bot_id", result.getBotID());
  }
}
