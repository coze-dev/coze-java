package com.coze.openapi.service.service.workflow;

import com.coze.openapi.api.WorkflowRunAPI;
import com.coze.openapi.api.WorkflowRunHistoryAPI;
import com.coze.openapi.client.workflows.run.ResumeRunReq;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.RunWorkflowResp;
import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.service.utils.Utils;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class WorkflowRunService {
    
    private final WorkflowRunAPI workflowRunAPI;

    private final WorkflowRunHistoryService historyService;

    public WorkflowRunService(WorkflowRunAPI runAPI, WorkflowRunHistoryAPI historyService) {
        this.workflowRunAPI = runAPI;
        this.historyService = new WorkflowRunHistoryService(historyService);
    }

    public RunWorkflowResp run(RunWorkflowReq req) {
        return Utils.execute(workflowRunAPI.runWorkflow(req));
    }

    public Flowable<WorkflowEvent> stream(RunWorkflowReq req) {
        return stream(workflowRunAPI.stream(req));
    }

    public Flowable<WorkflowEvent> resume(ResumeRunReq req) {
        return stream(workflowRunAPI.resume(req));
    }


    public static Flowable<WorkflowEvent> stream(Call<ResponseBody> apiCall) {
        return Flowable.create(emitter -> apiCall.enqueue(new EventCallback(emitter)), BackpressureStrategy.BUFFER);
    }

    public WorkflowRunHistoryService history(){
        return historyService;
    }
    
}
