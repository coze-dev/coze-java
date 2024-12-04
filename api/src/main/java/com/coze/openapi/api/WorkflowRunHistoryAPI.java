package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workflows.run.model.WorkflowRunHistory;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface WorkflowRunHistoryAPI {
    @GET("/v1/workflows/{workflow_id}/run_histories/{execute_id}")
    Single<BaseResponse<List<WorkflowRunHistory>>> retrieve(@Path("workflow_id")String workflow_id, @Path("execute_id")String execute_id);

}
