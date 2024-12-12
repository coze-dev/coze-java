package com.coze.openapi.service.service.workflow;

import com.coze.openapi.api.WorkflowRunAPI;
import com.coze.openapi.client.workflows.run.GetRunHistoryReq;
import com.coze.openapi.client.workflows.run.GetRunHistoryResp;
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
    
    private final WorkflowRunAPI workflowRunApi;

    public WorkflowRunService(WorkflowRunAPI workflowRunApi) {
        this.workflowRunApi = workflowRunApi;
    }

    public RunWorkflowResp run(RunWorkflowReq req) {
        return Utils.execute(workflowRunApi.runWorkflow(req));
    }

    public Flowable<WorkflowEvent> stream(RunWorkflowReq req) {
        return stream(workflowRunApi.streamRun(req));
    }

    public Flowable<WorkflowEvent> resumeRun(ResumeRunReq req) {
        return stream(workflowRunApi.resumeRun(req));
    }

    public GetRunHistoryResp getRunHistory(GetRunHistoryReq req) {
        return Utils.execute(workflowRunApi.getRunHistory(req.getWorkflowID(), req.getExecuteID()));
    }

    public static Flowable<WorkflowEvent> stream(Call<ResponseBody> apiCall) {
        return Flowable.create(emitter -> apiCall.enqueue(new EventCallback(emitter)), BackpressureStrategy.BUFFER);
    }
    
}
