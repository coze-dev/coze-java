package com.coze.openapi.service.auth;


import com.coze.openapi.client.auth.GetAccessTokenResp;
import com.coze.openapi.client.auth.GrantType;
import org.jetbrains.annotations.NotNull;

public class WebOAuthClient extends OAuthClient {

    public WebOAuthClient(String clientSecret, String clientID) {
        super(clientSecret, clientID);
    }

    public WebOAuthClient(String clientSecret, String clientID, String baseURL) {
        super(clientSecret, clientID, baseURL);
    }

    @Override
    public String getOauthURL(@NotNull String redirectURI, String state) {
        return super.getOauthURL(redirectURI, state);
    }

    @Override
    public String getOauthURL(@NotNull String redirectURI, String state, @NotNull String workspaceID) {
        return super.getOauthURL(redirectURI, state, workspaceID);
    }

    public GetAccessTokenResp getAccessToken(String code, String redirectURI) {
        return super.getAccessToken(GrantType.AuthorizationCode, code, this.clientSecret, redirectURI);
    }


    @Override
    public GetAccessTokenResp refreshToken(String refreshToken, String redirectURI) {
        return super.refreshAccessToken(refreshToken, this.clientSecret, redirectURI);
    }
}
