package com.coze.openapi.service.service.workflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.WorkflowAPI;
import com.coze.openapi.api.WorkflowChatAPI;
import com.coze.openapi.api.WorkflowRunAPI;
import com.coze.openapi.api.WorkflowRunHistoryAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workflows.WorkflowGetReq;
import com.coze.openapi.client.workflows.WorkflowGetResp;
import com.coze.openapi.client.workflows.WorkflowListReq;
import com.coze.openapi.client.workflows.WorkflowListResp;
import com.coze.openapi.client.workflows.model.WorkflowDetail;
import com.coze.openapi.client.workflows.model.WorkflowInput;
import com.coze.openapi.client.workflows.model.WorkflowOutput;
import com.coze.openapi.client.workflows.model.WorkflowParameter;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

public class WorkFlowServiceTest {

  @Mock private WorkflowAPI workflowAPI;

  @Mock private WorkflowRunAPI workflowRunAPI;

  @Mock private WorkflowRunHistoryAPI workflowRunHistoryAPI;

  @Mock private WorkflowChatAPI workflowChatAPI;

  private WorkflowService workflowService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    workflowService =
        new WorkflowService(workflowAPI, workflowRunAPI, workflowRunHistoryAPI, workflowChatAPI);
  }

  @Test
  void testList() throws Exception {
    // 准备测试数据
    String workSpaceId = "test_space_id";
    String workflowID = "test_workflow_id";
    Integer pageNum = 1;

    WorkflowListReq req =
        WorkflowListReq.builder().workspaceId(workSpaceId).pageNum(pageNum).build();

    WorkflowDetail detail =
        WorkflowDetail.builder()
            .workflowId(workflowID)
            .workflowName("测试工作流")
            .description("这是一个用于单元测试的工作流")
            .createdAt("2024-01-01T00:00:00Z")
            .updatedAt("2024-01-02T00:00:00Z")
            .build();

    WorkflowListResp data =
        WorkflowListResp.builder().items(Arrays.asList(detail)).hasMore(true).build();

    BaseResponse<WorkflowListResp> baseResponse =
        BaseResponse.<WorkflowListResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(data)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<WorkflowListResp>> call = mock(Call.class);
    when(workflowAPI.list(any(), any(), any(), any(), any(), any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    BaseResponse<WorkflowListResp> result = workflowService.list(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(workflowID, result.getData().getItems().get(0).getWorkflowId());
  }

  @Test
  void testGet() throws Exception {
    // 准备测试数据
    String workflowID = "test_workflow_id";
    boolean includeInputOutput = true;

    WorkflowGetReq req =
        WorkflowGetReq.builder()
            .workflowId(workflowID)
            .includeInputOutput(includeInputOutput)
            .build();

    Map<String, WorkflowParameter> inputParameters = new HashMap<>();
    inputParameters.put(
        "test_param1",
        WorkflowParameter.builder()
            .type("string")
            .required(true)
            .description("测试参数1")
            .defaultValue("param1")
            .build());
    inputParameters.put(
        "test_param2",
        WorkflowParameter.builder()
            .type("string")
            .required(false)
            .description("测试参数2")
            .defaultValue("param2")
            .build());
    WorkflowInput input = WorkflowInput.builder().parameters(inputParameters).build();

    WorkflowOutput output =
        WorkflowOutput.builder().terminatePlan("use_answer_content").content("这里是测试输出参数").build();

    WorkflowDetail detail =
        WorkflowDetail.builder()
            .workflowId(workflowID)
            .workflowName("测试工作流")
            .description("这是一个用于单元测试的工作流")
            .createdAt("2024-01-01T00:00:00Z")
            .updatedAt("2024-01-02T00:00:00Z")
            .build();

    WorkflowGetResp data =
        WorkflowGetResp.builder().input(input).output(output).workflowDetail(detail).build();

    BaseResponse<WorkflowGetResp> baseResponse =
        BaseResponse.<WorkflowGetResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(data)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<WorkflowGetResp>> call = mock(Call.class);
    when(workflowAPI.get(any(), any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    BaseResponse<WorkflowGetResp> result = workflowService.get(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(workflowID, result.getData().getWorkflowDetail().getWorkflowId());
  }
}
