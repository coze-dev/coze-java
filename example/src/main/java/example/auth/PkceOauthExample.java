package example.auth;

import com.coze.openapi.client.auth.GetAccessTokenResp;
import com.coze.openapi.service.auth.PKCEOAuthClient;

public class PkceOauthExample {

    public static void main(String[] args) {
        String redirectURI = System.getenv("REDIRECT_URI");
        String clientID = System.getenv("CLIENT_ID");
        PKCEOAuthClient oAuthBase = new PKCEOAuthClient(clientID);
        String codeChallenge = "code_verifier";
        String oauthURL = oAuthBase.getOauthURL(redirectURI, "state", codeChallenge, PKCEOAuthClient.CodeChallengeMethod.S256);
        System.out.println(oauthURL);
        
        String code = "";

        GetAccessTokenResp resp = oAuthBase.getAccessToken(code, redirectURI, codeChallenge);
        GetAccessTokenResp resp2 = oAuthBase.refreshToken(resp.getRefreshToken(), redirectURI);
    }
} 