package com.coze.openapi.service.auth;


import com.coze.openapi.client.auth.OAuthToken;

import com.coze.openapi.client.auth.GrantType;
import org.jetbrains.annotations.NotNull;

public class WebOAuthClient extends OAuthClient {

    protected WebOAuthClient(OAuthBuilder builder) {
        super(builder);
    }


    @Override
    public String getOAuthURL(@NotNull String redirectURI, String state) {
        return super.getOAuthURL(redirectURI, state);
    }

    @Override
    public String getOAuthURL(@NotNull String redirectURI, String state, @NotNull String workspaceID) {
        return super.getOAuthURL(redirectURI, state, workspaceID);
    }

    public OAuthToken getAccessToken(String code, String redirectURI) {
        return super.getAccessToken(GrantType.AuthorizationCode, code, this.clientSecret, redirectURI);
    }


    @Override
    public OAuthToken refreshToken(String refreshToken) {
        return super.refreshAccessToken(refreshToken, this.clientSecret);
    }

    public static class WebOAuthBuilder extends OAuthBuilder<WebOAuthBuilder> {
        @Override
        protected WebOAuthBuilder self() {
            return this;
        }

        @Override
        public WebOAuthClient build() {
            return new WebOAuthClient(this);
        }
    }
}
