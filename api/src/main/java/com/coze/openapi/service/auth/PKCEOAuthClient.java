package com.coze.openapi.service.auth;

import com.coze.openapi.client.auth.GetAccessTokenReq;
import com.coze.openapi.client.auth.GetAccessTokenResp;
import com.coze.openapi.client.auth.GrantType;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PKCEOAuthClient extends OAuthClient{

    @Getter
    public enum CodeChallengeMethod {
        Plain("plain"),
        S256("S256");

        private final String value;
        CodeChallengeMethod(String value) {
            this.value = value;
        }
    }

    public PKCEOAuthClient(String clientID) {
        super(null, clientID);
    }

    public PKCEOAuthClient(String clientID, String baseURL) {
        super(null, clientID, baseURL);
    }

    public String getOauthURL(@NotNull String redirectURI, String state, @NotNull String codeChallenge) {
        return super.getOauthURL(redirectURI, state, codeChallenge, CodeChallengeMethod.Plain.getValue());
    }

    public String getOauthURL(@NotNull String redirectURI, String state, @NotNull String codeChallenge, @NotNull String workspaceID) {
        return super.getOauthURL(redirectURI, state, codeChallenge, CodeChallengeMethod.Plain.getValue(), workspaceID);
    }

    public String getOauthURL(@NotNull String redirectURI, String state, @NotNull String codeChallenge, @NotNull CodeChallengeMethod codeChallengeMethod) {
        String code = "";
        try{
            code = "plain".equals(codeChallengeMethod.getValue())  ?codeChallenge:genS256CodeChallenge(codeChallenge);
        } catch (NoSuchAlgorithmException e) {
            code = codeChallenge;
        }
        return super.getOauthURL(redirectURI, state, code, codeChallengeMethod.getValue());
    }

    public String getOauthURL(@NotNull String redirectURI, String state, @NotNull String codeChallenge, @NotNull CodeChallengeMethod codeChallengeMethod, @NotNull String workspaceID) {
        String code = "";
        try{
            code = "plain".equals(codeChallengeMethod.getValue())  ?codeChallenge:genS256CodeChallenge(codeChallenge);
        } catch (NoSuchAlgorithmException e) {
            code = codeChallenge;
        }
        return super.getOauthURL(redirectURI, state, code, codeChallengeMethod.getValue(), workspaceID);
    }

    public GetAccessTokenResp getAccessToken(@NotNull String code, @NotNull String redirectURI, @Nullable String codeVerifier) {
        GetAccessTokenReq.GetAccessTokenReqBuilder builder = GetAccessTokenReq.builder();
        builder.clientID(this.clientID).
                grantType(GrantType.AuthorizationCode.getValue()).
                code(code).redirectUri(redirectURI).codeVerifier(codeVerifier);
        return super.getAccessToken(null, builder.build());
    }


    @Override
    public GetAccessTokenResp refreshToken(String refreshToken, String redirectURI) {
        return super.refreshAccessToken(refreshToken, redirectURI);
    }

    public static String genS256CodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] sha256Hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
        String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(sha256Hash);
        codeChallenge = codeChallenge.replace("=", "");
        return codeChallenge;
    }

}
