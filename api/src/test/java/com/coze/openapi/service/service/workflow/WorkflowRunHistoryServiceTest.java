/* (C)2024 */
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

    List<WorkflowRunHistory> histories =
        Arrays.asList(
            WorkflowRunHistory.builder().executeID("node1").build(),
            WorkflowRunHistory.builder().executeID("node2").build());

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
  }
}
