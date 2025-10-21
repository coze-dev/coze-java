package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workflows.WorkflowGetResp;
import com.coze.openapi.client.workflows.WorkflowListResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Tag;

public interface WorkflowAPI {

  @GET("/v1/workflows")
  Call<BaseResponse<WorkflowListResp>> list(
      @Query("workspace_id") String workspaceId,
      @Query("page_num") Integer pageNum,
      @Query("page_size") Integer pageSize,
      @Query("workflow_mode") String workflowMode,
      @Query("app_id") String appId,
      @Query("publish_status") String publishStatus,
      @Tag BaseReq baseReq);

  @GET("/v1/workflows/{workflow_id}")
  Call<BaseResponse<WorkflowGetResp>> get(
      @Path("workflow_id") String workflowId,
      @Query("include_input_output") Boolean includeInputOutput,
      @Tag BaseReq baseReq);
}
