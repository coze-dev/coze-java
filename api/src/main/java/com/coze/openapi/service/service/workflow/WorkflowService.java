package com.coze.openapi.service.service.workflow;

import com.coze.openapi.api.WorkflowChatAPI;
import com.coze.openapi.api.WorkflowRunAPI;
import com.coze.openapi.api.WorkflowRunHistoryAPI;

public class WorkflowService {
  private final WorkflowRunService runService;
  private final WorkflowChatService chatService;

  public WorkflowService(
      WorkflowRunAPI api, WorkflowRunHistoryAPI historyAPI, WorkflowChatAPI chatAPI) {
    this.runService = new WorkflowRunService(api, historyAPI);
    this.chatService = new WorkflowChatService(chatAPI);
  }

  public WorkflowRunService runs() {
    return runService;
  }

  public WorkflowChatService chats() {
    return chatService;
  }
}
