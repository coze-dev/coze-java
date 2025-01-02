package com.coze.openapi.service.service.workflow;

import com.coze.openapi.api.WorkflowChatAPI;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.workflows.chat.WorkflowChatReq;
import com.coze.openapi.service.service.common.ChatStream;

import io.reactivex.Flowable;

public class WorkflowChatService {

  private final WorkflowChatAPI workflowChatAPI;

  public WorkflowChatService(WorkflowChatAPI workflowChatAPI) {
    this.workflowChatAPI = workflowChatAPI;
  }
  /*
   * Call the Chat API with streaming to send messages to a published Coze bot.
   * docs en: https://www.coze.com/docs/developer_guides/workflow_chat
   * docs zh: https://www.coze.cn/docs/developer_guides/workflow_chat
   * */
  public Flowable<ChatEvent> stream(WorkflowChatReq req) {
    return ChatStream.stream(workflowChatAPI.stream(req, req));
  }
}
