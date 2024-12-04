package com.coze.openapi.service.service.workflow;

import com.coze.openapi.api.WorkflowRunHistoryAPI;
import com.coze.openapi.client.workflows.run.RetrieveRunHistoryReq;
import com.coze.openapi.client.workflows.run.model.WorkflowRunHistory;
import com.coze.openapi.service.utils.Utils;

public class WorkflowRunHistoryService {
    private final WorkflowRunHistoryAPI workflowRunHistoryAPI;

    public WorkflowRunHistoryService(WorkflowRunHistoryAPI workflowRunAPI) {
        this.workflowRunHistoryAPI = workflowRunAPI;
    }

    /*
     * After the workflow runs async, retrieve the execution results.
     * docs cn: https://www.coze.cn/docs/developer_guides/workflow_history
     * docs en: https://www.coze.com/docs/developer_guides/workflow_history
     * */
    public WorkflowRunHistory retrieve(RetrieveRunHistoryReq req) {
        return Utils.execute(workflowRunHistoryAPI.retrieve(req.getWorkflowID(), req.getExecuteID())).getData().get(0);
    }
}
