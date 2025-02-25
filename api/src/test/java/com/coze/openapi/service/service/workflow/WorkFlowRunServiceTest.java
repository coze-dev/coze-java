package com.coze.openapi.service.service.workflow;

import static com.coze.openapi.service.service.workflow.WorkflowRunService.stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.workflows.run.model.WorkflowEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.WorkflowRunAPI;
import com.coze.openapi.api.WorkflowRunHistoryAPI;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.RunWorkflowResp;
import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.utils.Utils;

import io.reactivex.subscribers.TestSubscriber;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.Calls;

public class WorkFlowRunServiceTest {

  private String eventData =
      "id: 0\n"
          + "event: Message\n"
          + "data: {\"content\":\"msg\",\"node_is_finish\":false,\"node_seq_id\":\"0\",\"node_title\":\"Message\"}\n"
          + "\n"
          + "id: 1\n"
          + "event: Message\n"
          + "data: {\"content\":\"为\",\"node_is_finish\":false,\"node_seq_id\":\"1\",\"node_title\":\"Message\"}\n"
          + "\n"
          + "id: 2\n"
          + "event: Message\n"
          + "data: {\"content\":\"什么小明要带一把尺子去看电影因\",\"node_is_finish\":false,\"node_seq_id\":\"2\",\"node_title\":\"Message\"}\n"
          + "\n"
          + "id: 3\n"
          + "event: Message\n"
          + "data: {\"content\":\"为他听说电影很长，怕\",\"node_is_finish\":false,\"node_seq_id\":\"3\",\"node_title\":\"Message\"}\n"
          + "\n"
          + "id: 4\n"
          + "event: Message\n"
          + "data: {\"content\":\"坐不下！\",\"node_is_finish\":true,\"node_seq_id\":\"4\",\"node_title\":\"Message\"}\n"
          + "\n"
          + "id: 5\n"
          + "event: Message\n"
          + "data: {\"content\":\"{\\\"output\\\":\\\"为什么小明要带一把尺子去看电影？因为他听说电影很长，怕坐不下！\\\"}\",\"cost\":\"0.00\",\"node_is_finish\":true,\"node_seq_id\":\"0\",\"node_title\":\"\",\"token\":1230}\n"
          + "\n"
          + "id: 0\n"
          + "event: Error\n"
          + "data: {\"error_code\":4000,\"error_message\":\"Request parameter error\"}\n"
          + "\n"
          + "id: 0\n"
          + "event: Message\n"
          + "data: {\"content\":\"请问你想查看哪个城市、哪一天的天气呢\",\"content_type\":\"text\",\"node_is_finish\":true,\"node_seq_id\":\"0\",\"node_title\":\"问答\"}\n"
          + "\n"
          + "id: 1\n"
          + "event: Interrupt\n"
          + "data: {\"interrupt_data\":{\"data\":\"\",\"event_id\":\"7404830425073352713/2769808280134765896\",\"type\":2},\"node_title\":\"问答\"}\n"
          + "\n"
          + "id: 6\n"
          + "event: Done\n"
          + "data: {\"debug_url\":\"https://www.coze.cn/work_flow?***\"}";

  @Mock private WorkflowRunAPI workflowRunAPI;

  @Mock private WorkflowRunHistoryAPI workflowRunHistoryAPI;

  private WorkflowRunService workflowRunService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    workflowRunService = new WorkflowRunService(workflowRunAPI, workflowRunHistoryAPI);
  }

  @Test
  void parseStreamEventTest() {
    // 准备 SSE 格式的响应数据
    // 使用 okio 的 Buffer 创建模拟的响应流
    ResponseBody responseBody =
        ResponseBody.create(eventData, MediaType.parse("text/event-stream; charset=utf-8"));

    // 模拟 API 调用
    Call<ResponseBody> call = Calls.response(responseBody);
    TestSubscriber<WorkflowEvent> testSubscriber = new TestSubscriber<>();
    stream(call).subscribe(testSubscriber);

    testSubscriber.awaitTerminalEvent(5, TimeUnit.SECONDS);

    testSubscriber.assertNoErrors();
    testSubscriber.assertValueCount(10);
    testSubscriber
            .assertValueAt(5,
            event ->
                    event.getEvent().equals(WorkflowEventType.MESSAGE)
                    && event.getMessage().getToken().equals(1230)
            )
            .assertValueAt(
            9,
            event ->
                event.getEvent().equals(WorkflowEventType.DONE)
                    && event.getDebugUrl().getDebugURL().equals("https://www.coze.cn/work_flow?***")
    );
  }

  @Test
  void testCreate() throws Exception {
    // 准备测试数据
    String workflowID = "test_workflow_id";
    String executeID = "test_execute_id";

    RunWorkflowReq req = RunWorkflowReq.builder().workflowID(workflowID).build();

    RunWorkflowResp baseResponse =
        RunWorkflowResp.builder()
            .code(0)
            .msg("success")
            .executeID(executeID)
            .logID(Utils.TEST_LOG_ID)
            .data("data")
            .build();

    // 创建 mock Call 对象
    Call<RunWorkflowResp> call = mock(Call.class);
    when(workflowRunAPI.run(any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    RunWorkflowResp result = workflowRunService.create(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(executeID, result.getExecuteID());
  }

  @Test
  void testHistories() {
    // 验证 histories 方法
    WorkflowRunHistoryService historyService = workflowRunService.histories();
    assertNotNull(historyService);
  }
}
