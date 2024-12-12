package com.coze.openapi.api;

import com.coze.openapi.client.workflows.run.GetRunHistoryResp;
import com.coze.openapi.client.workflows.run.ResumeRunReq;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.RunWorkflowResp;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface WorkflowRunAPI {

    @POST("/v1/workflow/run")
    Single<RunWorkflowResp> runWorkflow(@Body RunWorkflowReq req);

    @POST("/v1/workflow/stream_run")
    @Streaming
    Call<ResponseBody> streamRun(@Body RunWorkflowReq req);

    @GET("/v1/workflows/{workflow_id}/run_histories/{execute_id}")
    Single<GetRunHistoryResp> getRunHistory(@Path("workflow_id")String wid, @Path("execute_id")String eid);

    @POST("/v1/workflow/stream_resume")
    @Streaming
    Call<ResponseBody> resumeRun(@Body ResumeRunReq req);
}