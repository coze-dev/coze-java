package com.coze.openapi.service.service.workflow;

import com.coze.openapi.api.WorkflowRunHistoryAPI;
import com.coze.openapi.client.workflows.run.RetrieveRunHistoryReq;
import com.coze.openapi.client.workflows.run.RetrieveRunHistoryResp;
import com.coze.openapi.service.utils.Utils;

public class WorkflowRunHistoryService {
    private final WorkflowRunHistoryAPI workflowRunHistoryAPI;

    public WorkflowRunHistoryService(WorkflowRunHistoryAPI workflowRunAPI) {
        this.workflowRunHistoryAPI = workflowRunAPI;
    }

    // history.retrive
    public RetrieveRunHistoryResp retrieve(RetrieveRunHistoryReq req) {
        return Utils.execute(workflowRunHistoryAPI.retrieve(req.getWorkflowID(), req.getExecuteID()));
    }
}
