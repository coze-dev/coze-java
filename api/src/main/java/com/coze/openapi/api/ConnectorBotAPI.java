package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connectors.bots.AuditReq;
import com.coze.openapi.client.connectors.bots.AuditResp;

import retrofit2.Call;
import retrofit2.http.*;

public interface ConnectorBotAPI {
  @Headers({"Content-Type: application/json"})
  @POST("v1/connectors/{connector_id}/bots/{bot_id}")
  Call<BaseResponse<AuditResp>> audit(
      @Path("connector_id") String connectorId,
      @Path("bot_id") String botId,
      @Body AuditReq req,
      @Tag BaseReq baseReq);
}
