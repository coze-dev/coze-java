package com.coze.openapi.service.auth;

import com.coze.openapi.client.auth.GetAccessTokenResp;

public abstract class Auth {    

    protected String accessToken;
    protected String refreshToken;
    protected long expiresIn;
    protected OAuthClient client;
    protected String redirectURI;


    protected boolean needRefresh(){
        // accessToken 为空代表第一次请求,需要刷新token
        if (accessToken ==null || System.currentTimeMillis() / 1000 > expiresIn) {
            return true;
        }
        return false;
    }

    /**
     * 获取token类型
     * @return token类型，默认返回 Bearer
     */
    public String tokenType() {
        return "Bearer";
    }

    /**
     * 获取token
     * @return token
     */
    public String token(){
        if (!this.needRefresh()) {
            return accessToken;
        }

        GetAccessTokenResp resp = this.client.refreshToken(this.refreshToken, this.redirectURI);
        this.accessToken = resp.getAccessToken();
        this.refreshToken = resp.getRefreshToken();
        this.expiresIn = resp.getExpiresIn();
        return this.accessToken;
    }

}
