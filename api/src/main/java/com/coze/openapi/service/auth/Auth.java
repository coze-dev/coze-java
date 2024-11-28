package com.coze.openapi.service.auth;

import com.coze.openapi.client.auth.OAuthToken;

public abstract class Auth {    

    protected String accessToken;
    protected String refreshToken;
    protected long expiresIn;
    protected OAuthClient client;


    protected boolean needRefresh(){
        // accessToken 为空代表第一次请求,需要刷新token
        return accessToken == null || System.currentTimeMillis() / 1000 > expiresIn;
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

        OAuthToken resp = this.client.refreshToken(this.refreshToken);
        this.accessToken = resp.getAccessToken();
        this.refreshToken = resp.getRefreshToken();
        this.expiresIn = resp.getExpiresIn();
        return this.accessToken;
    }

}
