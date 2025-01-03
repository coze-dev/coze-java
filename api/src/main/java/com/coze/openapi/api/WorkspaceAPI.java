package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workspace.ListWorkspaceResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Tag;

public interface WorkspaceAPI {

  @Headers({"Content-Type: application/json"})
  @GET("/v1/workspaces")
  Call<BaseResponse<ListWorkspaceResp>> list(
      @Query("page_num") Integer page, @Query("page_size") Integer pageSize, @Tag BaseReq baseReq);
}
