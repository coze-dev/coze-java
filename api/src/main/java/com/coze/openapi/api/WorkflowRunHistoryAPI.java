package com.coze.openapi.api;

import java.util.List;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workflows.run.model.WorkflowRunHistory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Tag;

public interface WorkflowRunHistoryAPI {
  @GET("/v1/workflows/{workflow_id}/run_histories/{execute_id}")
  Call<BaseResponse<List<WorkflowRunHistory>>> retrieve(
      @Path("workflow_id") String workflow_id,
      @Path("execute_id") String execute_id,
      @Tag BaseReq baseReq);
}
