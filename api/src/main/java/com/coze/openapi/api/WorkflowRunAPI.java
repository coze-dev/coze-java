package com.coze.openapi.api;

import com.coze.openapi.client.workflows.run.ResumeRunReq;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.RunWorkflowResp;
import com.coze.openapi.client.common.BaseReq;

import io.reactivex.Single;
import retrofit2.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Tag;

public interface WorkflowRunAPI {

    @POST("/v1/workflow/run")
    Single<Response<RunWorkflowResp>> run(@Body RunWorkflowReq req, @Tag BaseReq baseReq);

    @POST("/v1/workflow/stream_run")
    @Streaming
    Call<ResponseBody> stream(@Body RunWorkflowReq req, @Tag BaseReq baseReq);

    @POST("/v1/workflow/stream_resume")
    @Streaming
    Call<ResponseBody> resume(@Body ResumeRunReq req, @Tag BaseReq baseReq);
}