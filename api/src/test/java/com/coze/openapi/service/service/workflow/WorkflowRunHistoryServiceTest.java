package com.coze.openapi.service.service.workflow;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.WorkflowRunHistoryAPI;
import com.coze.openapi.client.chat.model.ChatUsage;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workflows.run.RetrieveRunHistoryReq;
import com.coze.openapi.client.workflows.run.RetrieveRunHistoryResp;
import com.coze.openapi.client.workflows.run.model.WorkflowRunHistory;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

class WorkflowRunHistoryServiceTest {

  @Mock private WorkflowRunHistoryAPI historyAPI;

  private WorkflowRunHistoryService historyService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    historyService = new WorkflowRunHistoryService(historyAPI);
  }

  @Test
  void testRetrieve() throws Exception {
    // 准备测试数据
    String workflowId = "workflow_id";
    String executeId = "execute_id";
    RetrieveRunHistoryReq req =
        RetrieveRunHistoryReq.builder().workflowID(workflowId).executeID(executeId).build();

    ChatUsage usage1 = ChatUsage.builder().tokenCount(150).inputCount(75).outputCount(75).build();

    ChatUsage usage2 = ChatUsage.builder().tokenCount(200).inputCount(100).outputCount(100).build();

    List<WorkflowRunHistory> histories =
        Arrays.asList(
            WorkflowRunHistory.builder().executeID("node1").usage(usage1).build(),
            WorkflowRunHistory.builder().executeID("node2").usage(usage2).build());

    BaseResponse<List<WorkflowRunHistory>> baseResponse =
        BaseResponse.<List<WorkflowRunHistory>>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(histories)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<List<WorkflowRunHistory>>> call = mock(Call.class);
    when(historyAPI.retrieve(eq(workflowId), eq(executeId), any(RetrieveRunHistoryReq.class)))
        .thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    RetrieveRunHistoryResp result = historyService.retrieve(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(2, result.getHistories().size());
    assertEquals("node1", result.getHistories().get(0).getExecuteID());
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    assertNotNull(result.getHistories().get(0).getUsage());
    assertEquals(150, result.getHistories().get(0).getUsage().getTokenCount());
    assertEquals(75, result.getHistories().get(0).getUsage().getInputCount());
    assertEquals(75, result.getHistories().get(0).getUsage().getOutputCount());
    assertNotNull(result.getHistories().get(1).getUsage());
    assertEquals(200, result.getHistories().get(1).getUsage().getTokenCount());
    assertEquals(100, result.getHistories().get(1).getUsage().getInputCount());
    assertEquals(100, result.getHistories().get(1).getUsage().getOutputCount());
  }
}
