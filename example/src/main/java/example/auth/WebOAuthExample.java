package example.auth;

import com.coze.openapi.client.auth.GetAccessTokenResp;
import com.coze.openapi.service.auth.WebOAuthClient;

public class WebOAuthExample {

    public static void main(String[] args) {
        String redirectURI = System.getenv("REDIRECT_URI");
        String clientSecret = System.getenv("CLIENT_SECRET");
        String clientID = System.getenv("CLIENT_ID");
        WebOAuthClient oAuthBase = new WebOAuthClient(clientSecret, clientID);
        String oauthURL = oAuthBase.getOauthURL(redirectURI, "state");
        System.out.println(oauthURL);
        
        String code = "";
        GetAccessTokenResp resp = oAuthBase.getAccessToken(code, redirectURI);
        System.out.println(resp);
    }
} 