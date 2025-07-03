package com.coze.openapi.api;

import java.util.Map;

import com.coze.openapi.client.auth.*;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.*;

public interface CozeAuthAPI {
  @Headers({"Content-Type: application/json"})
  @POST("/api/permission/oauth2/token")
  Single<Response<OAuthToken>> retrieve(
      @HeaderMap Map<String, String> headers, @Body GetAccessTokenReq req);

  @Headers({"Content-Type: application/json"})
  @POST("/api/permission/oauth2/device/code")
  Single<Response<DeviceAuthResp>> device(@Body DeviceAuthReq req);

  @Headers({"Content-Type: application/json"})
  @POST("/api/permission/oauth2/workspace_id/{workspace_id}/device/code")
  Single<Response<DeviceAuthResp>> device(
      @Path("workspace_id") String workspaceID, @Body DeviceAuthReq req);

  @Headers({"Content-Type: application/json"})
  @POST("/api/permission/oauth2/account/{account_id}/token")
  Single<Response<OAuthToken>> account(
      @HeaderMap Map<String, String> headers,
      @Path("account_id") String accountID,
      @Body GetAccessTokenReq req);

  @Headers({"Content-Type: application/json"})
  @POST("/api/permission/oauth2/enterprise_id/{enterprise_id}/token")
  Single<Response<OAuthToken>> enterprise(
      @HeaderMap Map<String, String> headers,
      @Path("enterprise_id") String enterpriseID,
      @Body GetAccessTokenReq req);
}
