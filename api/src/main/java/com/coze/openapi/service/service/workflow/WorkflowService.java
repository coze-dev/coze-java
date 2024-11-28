package com.coze.openapi.service.service.workflow;

import com.coze.openapi.api.WorkflowRunAPI;
import com.coze.openapi.api.WorkflowRunHistoryAPI;

public class WorkflowService {
    private final WorkflowRunService runService;

    public WorkflowService(WorkflowRunAPI api, WorkflowRunHistoryAPI historyAPI) {
        this.runService = new WorkflowRunService(api, historyAPI);
    }

    public WorkflowRunService runs(){
        return runService;
    }
}
