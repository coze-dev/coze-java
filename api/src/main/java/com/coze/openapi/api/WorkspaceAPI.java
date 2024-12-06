package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workspace.ListWorkspaceResp;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import com.coze.openapi.client.common.BaseReq;
import retrofit2.http.Tag;

public interface WorkspaceAPI {

    @Headers({"Content-Type: application/json"})
    @GET("/v1/workspaces")
    Single<Response<BaseResponse<ListWorkspaceResp>>> list(@Query("page_num") Integer page, @Query("page_size") Integer pageSize, @Tag BaseReq baseReq );
}
