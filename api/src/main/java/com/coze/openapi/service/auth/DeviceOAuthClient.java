package com.coze.openapi.service.auth;

import com.coze.openapi.client.auth.*;
import com.coze.openapi.client.exception.AuthErrorCode;
import com.coze.openapi.client.exception.CozeAuthException;


import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

public class DeviceOAuthClient extends OAuthClient{

    protected DeviceOAuthClient(DeviceOAuthBuilder builder) {
        super(builder);
    }

    private static final Logger logger = AuthLogFactory.getLogger();
    public DeviceAuthResp getDeviceCode(){
        DeviceAuthReq req = DeviceAuthReq.builder().clientID(this.clientID).build();
        DeviceAuthResp resp = execute(this.api.device(req));
        resp.setVerificationURL(resp.getVerificationURI() + "?user_code=" + resp.getUserCode());
        return resp;
    }

    public DeviceAuthResp getDeviceCode(@NotNull String workspaceID){
        DeviceAuthReq req = DeviceAuthReq.builder().clientID(this.clientID).build();
        DeviceAuthResp resp = execute(this.api.device(workspaceID, req));
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
            return super.getAccessToken(null, builder.build()); // secret 放进去
        }
        int interval = 5;
        while (true){
            try{
                return super.getAccessToken(null, builder.build());
            }catch (CozeAuthException e){
                if (AuthErrorCode.AUTHORIZATION_PENDING.equals(e.getCode())){
                    logger.info("Authorization pending, sleep " + interval + " seconds");
                } else if (AuthErrorCode.SLOW_DOWN.equals(e.getCode())){
                    if (interval < 30){
                        interval+=5;
                    }
                    logger.info("Slow down, sleep " + interval + " seconds");
                } else {
                    throw e;
                }
                try {
                    TimeUnit.SECONDS.sleep(interval);
                } catch (InterruptedException ie) {
                    logger.warn("Interrupted while sleeping", ie);
                    Thread.currentThread().interrupt(); // 恢复中断状态
                    throw ie;
                }
            }catch (Exception e){
                throw e;
            }
        }
    }



    @Override
    public OAuthToken refreshToken(String refreshToken) {
        return super.refreshAccessToken(refreshToken, this.clientSecret);
    }

    public static class DeviceOAuthBuilder extends OAuthBuilder<DeviceOAuthBuilder> {
        @Override
        protected DeviceOAuthBuilder self() {
            return this;
        }

        @Override
        public DeviceOAuthClient build() {
            return new DeviceOAuthClient(this);
        }
    }
}
