package com.coze.openapi.service.auth;

import com.coze.openapi.client.auth.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Struct;

public class DeviceOAuthClient extends OAuthClient{

    public DeviceOAuthClient(String clientID) {
        super(null, clientID);
    }

    public DeviceOAuthClient(String clientID, String baseURL) {
        super(null, clientID, baseURL);
    }

    public DeviceAuthResp getOauthURL(){
        DeviceAuthReq req = DeviceAuthReq.builder().clientID(this.clientID).build();
        DeviceAuthResp resp = execute(this.api.DeviceAuth(req));
        resp.setVerificationUri(resp.getVerificationUri() + "?user_code=" + resp.getUserCode());
        return resp;
    }

    public DeviceAuthResp getOauthURL(@NotNull String workspaceID){
        DeviceAuthReq req = DeviceAuthReq.builder().clientID(this.clientID).build();
        DeviceAuthResp resp = execute(this.api.DeviceAuth(workspaceID, req));
        resp.setVerificationUri(resp.getVerificationUri() + "?user_code=" + resp.getUserCode());
        return resp;
    }

    public GetAccessTokenResp getAccessToken(String deviceCode) {
        GetAccessTokenReq.GetAccessTokenReqBuilder builder = GetAccessTokenReq.builder();
        builder.clientID(this.clientID).
                grantType(GrantType.DeviceCode.getValue()).deviceCode(deviceCode);
        GetAccessTokenResp resp = super.getAccessToken(null, builder.build());
        return resp;
    }

    protected GetAccessTokenResp getAccessToken(String code, String redirectURI) {
        return super.getAccessToken(GrantType.AuthorizationCode, code, this.clientSecret, redirectURI);
    }


    @Override
    public GetAccessTokenResp refreshToken(String refreshToken, String redirectURI) {
        return super.refreshAccessToken(refreshToken, this.clientSecret, redirectURI);
    }
}
