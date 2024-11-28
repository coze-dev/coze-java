package com.coze.openapi.api;

import com.coze.openapi.client.workflows.run.RetrieveRunHistoryResp;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WorkflowRunHistoryAPI {
    @GET("/v1/workflows/{workflow_id}/run_histories/{execute_id}")
    Single<RetrieveRunHistoryResp> retrieve(@Path("workflow_id")String workflow_id, @Path("execute_id")String execute_id);

}
