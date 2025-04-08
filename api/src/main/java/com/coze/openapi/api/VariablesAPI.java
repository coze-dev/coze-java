package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.variables.RetrieveVariablesResp;
import com.coze.openapi.client.variables.UpdateVariablesReq;
import com.coze.openapi.client.variables.UpdateVariablesResp;

import retrofit2.Call;
import retrofit2.http.*;

public interface VariablesAPI {
  @Headers({"Content-Type: application/json"})
  @PUT("/v1/variables")
  Call<BaseResponse<UpdateVariablesResp>> update(
      @Body UpdateVariablesReq req, @Tag BaseReq baseReq);

  @Headers({"Content-Type: application/json"})
  @GET("/v1/variables")
  Call<BaseResponse<RetrieveVariablesResp>> retrieve(
      @Query("app_id") String appID,
      @Query("bot_id") String botID,
      @Query("connector_id") String connectorID,
      @Query("connector_uid") String connectorUID,
      @Query("keywords") String keywords,
      @Tag BaseReq baseReq);
}
