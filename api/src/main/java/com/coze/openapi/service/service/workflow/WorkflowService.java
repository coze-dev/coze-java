package com.coze.openapi.service.service.workflow;

import com.coze.openapi.api.WorkflowRunAPI;

public class WorkflowService {
    private final WorkflowRunService workflowRunService;

    public WorkflowService(WorkflowRunAPI api) {
        this.workflowRunService = new WorkflowRunService(api);
    }

    public WorkflowRunService run(){
        return workflowRunService;
    }
}
