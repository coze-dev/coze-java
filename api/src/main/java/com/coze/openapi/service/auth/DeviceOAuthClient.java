package com.coze.openapi.service.auth;

import com.coze.openapi.client.auth.*;
import com.coze.openapi.client.exception.CozeAuthException;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class DeviceOAuthClient extends OAuthClient{

    public DeviceOAuthClient(String clientID) {
        super(clientID, null);
    }

    public DeviceOAuthClient(String clientID, String baseURL) {
        super(clientID, null, baseURL);
    }

    public DeviceAuthCode getDeviceCode(){
        DeviceAuthReq req = DeviceAuthReq.builder().clientID(this.clientID).build();
        DeviceAuthCode resp = execute(this.api.DeviceAuth(req));
        resp.setVerificationURL(resp.getVerificationURI() + "?user_code=" + resp.getUserCode());
        return resp;
    }

    public DeviceAuthCode getDeviceCode(@NotNull String workspaceID){
        DeviceAuthReq req = DeviceAuthReq.builder().clientID(this.clientID).build();
        DeviceAuthCode resp = execute(this.api.DeviceAuth(workspaceID, req));
        resp.setVerificationURL(resp.getVerificationURI() + "?user_code=" + resp.getUserCode());
        return resp;
    }

    public OAuthToken getAccessToken(String deviceCode) throws Exception {
        return getAccessToken(deviceCode, false);
    }

    public OAuthToken getAccessToken(String deviceCode, boolean poll) throws Exception{
        GetAccessTokenReq.GetAccessTokenReqBuilder builder = GetAccessTokenReq.builder();
        builder.clientID(this.clientID).
                grantType(GrantType.DeviceCode.getValue()).deviceCode(deviceCode);
        if (!poll){
            return super.getAccessToken(null, builder.build());
        }
        int interval = 5;
        while (true){
            try{
                return super.getAccessToken(null, builder.build());
            }catch (CozeAuthException e){
                int sleepTime = 5;
                switch (e.getCode()){
                    case AuthorizationPending:
                        break;
                    case SlowDown:
                        sleepTime = interval;
                        if (interval < 30){
                            interval+=5;
                        }
                        break;
                    default:
                        throw e;
                    }
                try {
                    TimeUnit.SECONDS.sleep(sleepTime);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // 恢复中断状态
                    throw ie;
                }
                }

            }
        }


    @Override
    public OAuthToken refreshToken(String refreshToken) {
        return super.refreshAccessToken(refreshToken, this.clientSecret);
    }
}
