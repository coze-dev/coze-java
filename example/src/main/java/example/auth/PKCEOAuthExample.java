package example.auth;

import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.auth.GetPKCEAuthURLResp;
import com.coze.openapi.service.auth.PKCEOAuthClient;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.CozeAPI;

/*
How to effectuate OpenAPI authorization through the OAuth Proof Key for Code Exchange method.

PKCE stands for Proof Key for Code Exchange, and it's an extension to the OAuth 2.0 authorization
code flow designed to enhance security for public clients, such as mobile and single-page
applications.

Firstly, users need to access https://www.coze.com/open/oauth/apps. For the cn environment,
users need to access https://www.coze.cn/open/oauth/apps to create an OAuth App of the type
of Mobile/PC/Single-page application.
The specific creation process can be referred to in the document:
https://www.coze.com/docs/developer_guides/oauth_pkce. For the cn environment, it can be
accessed at https://www.coze.cn/docs/developer_guides/oauth_pkce.
After the creation is completed, the client ID can be obtained.
* */
public class PKCEOAuthExample {

    public static void main(String[] args) {
        String redirectURI = System.getenv("COZE_PKCE_OAUTH_REDIRECT_URI");
        String clientID = System.getenv("COZE_PKCE_OAUTH_CLIENT_ID");

        /*
         * The default access is api.coze.com, but if you need to access api.coze.cn,
         * please use base_url to configure the api endpoint to access
         */
        String cozeAPIBase = System.getenv("COZE_API_BASE");
        if(cozeAPIBase==null|| cozeAPIBase.isEmpty()){
            cozeAPIBase = Consts.COZE_COM_BASE_URL;
        }

        PKCEOAuthClient oauth = new PKCEOAuthClient.PKCEOAuthBuilder()
                .clientID(clientID)
                .baseURL(cozeAPIBase)
                .build();

        /*
        In the SDK, we have wrapped up the code_challenge process of PKCE. Developers only need
        to select the code_challenge_method.
        * */
        GetPKCEAuthURLResp oauthURL = oauth.genOAuthURL(redirectURI, "state", PKCEOAuthClient.CodeChallengeMethod.S256);
        System.out.println(oauthURL);

        /*
         * The space permissions for which the Access Token is granted can be specified. As following codes:
         * */
        oauthURL = oauth.genOAuthURL(redirectURI, "state", PKCEOAuthClient.CodeChallengeMethod.S256, "workspaceID");
        System.out.println(oauthURL);
        
        /*
        After the user clicks the authorization consent button, the coze web page will redirect
        to the redirect address configured in the authorization link and carry the authorization
        code and state parameters in the address via the query string.

        Get from the query of the redirect interface: query.get('code')
        * */
        String code = "mock code";

        /*
        After obtaining the code after redirection, the interface to exchange the code for a
        token can be invoked to generate the coze access_token of the authorized user.
        * */
        OAuthToken resp = oauth.getAccessToken(code, redirectURI, "");
        System.out.println(resp);

        // use the access token to init Coze client
        CozeAPI coze = new CozeAPI.Builder().auth(new TokenAuth(resp.getAccessToken())).baseURL(cozeAPIBase).build();
        // When the token expires, you can also refresh and re-obtain the token
        resp = oauth.refreshToken(resp.getRefreshToken());

    }
} 