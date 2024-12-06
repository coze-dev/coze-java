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

    /*
     * Run the published workflow.
     * This API is in non-streaming response mode. For nodes that support streaming output,
     * you should run the API Run workflow (streaming response) to obtain streaming responses.
     *
     * docs en: https://www.coze.com/docs/developer_guides/workflow_run
     * docs cn: https://www.coze.cn/docs/developer_guides/workflow_run
    * */
    public RunWorkflowResp create(RunWorkflowReq req) {
        return Utils.execute(workflowRunAPI.run(req, req));
    }

    /*
     * Run the published workflow.
     * This API is in non-streaming response mode. For nodes that support streaming output,
     * you should run the API Run workflow (streaming response) to obtain streaming responses.
     *
     * docs en: https://www.coze.com/docs/developer_guides/workflow_run
     * docs cn: https://www.coze.cn/docs/developer_guides/workflow_run
     * */
    public Flowable<WorkflowEvent> stream(RunWorkflowReq req) {
        return stream(workflowRunAPI.stream(req, req));
    }

    /*
    *  docs cn: https://www.coze.cn/docs/developer_guides/workflow_resume
    *  docs en: https://www.coze.com/docs/developer_guides/workflow_resume
    * */
    public Flowable<WorkflowEvent> resume(ResumeRunReq req) {
        return stream(workflowRunAPI.resume(req, req));
    }


    public static Flowable<WorkflowEvent> stream(Call<ResponseBody> apiCall) {
        return Flowable.create(emitter -> apiCall.enqueue(new EventCallback(emitter)), BackpressureStrategy.BUFFER);
    }

    public WorkflowRunHistoryService histories(){
        return historyService;
    }
    
}
