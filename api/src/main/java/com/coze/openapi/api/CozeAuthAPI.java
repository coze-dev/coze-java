package com.coze.openapi.api;

import io.reactivex.Single;
import retrofit2.http.*;
import com.coze.openapi.client.auth.*;

import java.util.Map;

public interface CozeAuthAPI {
    @Headers({"Content-Type: application/json"})
    @POST("/api/permission/oauth2/token")
    Single<OAuthToken> OauthAccessToken(@HeaderMap Map<String, String> headers, @Body GetAccessTokenReq req);

    @Headers({"Content-Type: application/json"})
    @POST("/api/permission/oauth2/device/code")
    Single<DeviceAuthCode> DeviceAuth(@Body DeviceAuthReq req);

    @Headers({"Content-Type: application/json"})
    @POST("/api/permission/oauth2/workspace_id/{workspace_id}/device/code")
    Single<DeviceAuthCode> DeviceAuth(@Path("workspace_id") String workspaceID, @Body DeviceAuthReq req);
}
