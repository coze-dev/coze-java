package example.auth;

import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.auth.WebOAuthClient;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.CozeAPI;

/*
How to effectuate OpenAPI authorization through the OAuth authorization code method.

Firstly, users need to access https://www.coze.com/open/oauth/apps. For the cn environment,
users need to access https://www.coze.cn/open/oauth/apps to create an OAuth App of the type
of Web application.
The specific creation process can be referred to in the document:
https://www.coze.com/docs/developer_guides/oauth_code. For the cn environment, it can be
accessed at https://www.coze.cn/docs/developer_guides/oauth_code.
After the creation is completed, the client ID, client secret, and redirect link, can be
obtained. For the client secret, users need to keep it securely to avoid leakage.

* */
public class WebOAuthExample {

  public static void main(String[] args) {
    String redirectURI = System.getenv("COZE_WEB_OAUTH_REDIRECT_URI");
    String clientSecret = System.getenv("COZE_WEB_OAUTH_CLIENT_SECRET");
    String clientID = System.getenv("COZE_WEB_OAUTH_CLIENT_ID");

    /*
     * The default access is api.coze.com, but if you need to access api.coze.cn,
     * please use base_url to configure the api endpoint to access
     */
    String cozeAPIBase = System.getenv("COZE_API_BASE");
    if (cozeAPIBase == null || cozeAPIBase.isEmpty()) {
      cozeAPIBase = Consts.COZE_COM_BASE_URL;
    }

    /*
     The sdk offers the WebOAuthClient class to establish an authorization for Web OAuth.
     Firstly, it is required to initialize the WebOAuthApp with the client ID and client secret.
    */
    WebOAuthClient oauth =
        new WebOAuthClient.WebOAuthBuilder()
            .clientID(clientID)
            .clientSecret(clientSecret)
            .baseURL(cozeAPIBase)
            .build();

    // Generate the authorization link and direct the user to open it.
    String oauthURL = oauth.getOAuthURL(redirectURI, null);
    System.out.println(oauthURL);

    /*
     * The space permissions for which the Access Token is granted can be specified. As following codes:
     * */
    oauthURL = oauth.getOAuthURL(redirectURI, null, "workspaceID");
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
    OAuthToken resp = oauth.getAccessToken(code, redirectURI);
    System.out.println(resp);

    /*
     * you can get request log by getLogID method
     * */
    System.out.println(resp.getLogID());

    // use the access token to init Coze client
    CozeAPI coze =
        new CozeAPI.Builder()
            .auth(new TokenAuth(resp.getAccessToken()))
            .baseURL(cozeAPIBase)
            .build();
    // When the token expires, you can also refresh and re-obtain the token
    resp = oauth.refreshToken(resp.getRefreshToken());
  }
}
