package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workspace.ListWorkspaceResp;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WorkspaceAPI {

    @Headers({"Content-Type: application/json"})
    @GET("/v1/workspaces")
    Single<BaseResponse<ListWorkspaceResp>> ListWorkspaces(@Query("page_num") Integer page, @Query("page_size") Integer pageSize );
}
