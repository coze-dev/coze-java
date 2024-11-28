package com.coze.openapi.api;

import com.coze.openapi.client.workflows.run.ResumeRunReq;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.RunWorkflowResp;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

public interface WorkflowRunAPI {

    @POST("/v1/workflow/run")
    Single<RunWorkflowResp> runWorkflow(@Body RunWorkflowReq req);

    @POST("/v1/workflow/stream_run")
    @Streaming
    Call<ResponseBody> stream(@Body RunWorkflowReq req);

    @POST("/v1/workflow/stream_resume")
    @Streaming
    Call<ResponseBody> resume(@Body ResumeRunReq req);
}