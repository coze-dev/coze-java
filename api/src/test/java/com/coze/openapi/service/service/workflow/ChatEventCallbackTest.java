package com.coze.openapi.service.service.workflow;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.client.workflows.run.model.WorkflowEvent;

import io.reactivex.FlowableEmitter;

class ChatEventCallbackTest {

  @Mock private FlowableEmitter<WorkflowEvent> emitter;

  private EventCallback callback;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    callback = new EventCallback(emitter);
  }

  @Test
  void testProcessLine() throws Exception {
    // 准备测试数据
    String eventData =
        "id: 1\nevent: Message\ndata: {\"content\":\"为\",\"node_is_finish\":false,\"node_seq_id\":\"1\",\"node_title\":\"Message\"}";
    BufferedReader reader = new BufferedReader(new StringReader(eventData));

    // 执行测试
    callback.processLine(reader.readLine(), reader, "test_log_id");

    // 验证结果
    verify(emitter, times(1)).onNext(any(WorkflowEvent.class));
  }

  @Test
  void testProcessLineWithInvalidData() throws Exception {
    // 准备测试数据
    String eventData = "invalid data\n";
    BufferedReader reader = new BufferedReader(new StringReader(eventData));

    // 执行测试
    boolean result = callback.processLine("invalid", reader, "test_log_id");

    // 验证结果
    assertFalse(result);
    verify(emitter, never()).onNext(any(WorkflowEvent.class));
  }
}
