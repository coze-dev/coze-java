package com.coze.openapi.service.service.workflow;

import com.coze.openapi.api.WorkflowAPI;
import com.coze.openapi.api.WorkflowChatAPI;
import com.coze.openapi.api.WorkflowRunAPI;
import com.coze.openapi.api.WorkflowRunHistoryAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workflows.WorkflowGetReq;
import com.coze.openapi.client.workflows.WorkflowGetResp;
import com.coze.openapi.client.workflows.WorkflowListReq;
import com.coze.openapi.client.workflows.WorkflowListResp;
import com.coze.openapi.service.utils.Utils;

public class WorkflowService {
  private final WorkflowRunService runService;
  private final WorkflowChatService chatService;
  private final WorkflowAPI workflowAPI;

  public WorkflowService(
      WorkflowAPI api,
      WorkflowRunAPI runAPI,
      WorkflowRunHistoryAPI historyAPI,
      WorkflowChatAPI chatAPI) {
    this.runService = new WorkflowRunService(runAPI, historyAPI);
    this.chatService = new WorkflowChatService(chatAPI);
    this.workflowAPI = api;
  }

  public WorkflowRunService runs() {
    return runService;
  }

  public WorkflowChatService chat() {
    return chatService;
  }

  public BaseResponse<WorkflowListResp> list(WorkflowListReq req) {
    return Utils.execute(
        workflowAPI.list(
            req.getWorkspaceId(),
            req.getPageNum(),
            req.getPageSize(),
            req.getWorkflowMode(),
            req.getAppId(),
            req.getPublishStatus(),
            req));
  }

  public BaseResponse<WorkflowGetResp> get(WorkflowGetReq req) {
    return Utils.execute(workflowAPI.get(req.getWorkflowId(), req.getIncludeInputOutput(), req));
  }
}
