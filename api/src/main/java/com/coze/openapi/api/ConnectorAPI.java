package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connectors.InstallConnectorReq;
import com.coze.openapi.client.connectors.InstallConnectorResp;

import retrofit2.Call;
import retrofit2.http.*;

public interface ConnectorAPI {
  @Headers({"Content-Type: application/json"})
  @POST("v1/connectors/{connector_id}/install")
  Call<BaseResponse<InstallConnectorResp>> install(
      @Path("connector_id") String connectorId,
      @Body InstallConnectorReq req,
      @Tag BaseReq baseReq);
}
