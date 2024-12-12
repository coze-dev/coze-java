/* (C)2024 */
package example.auth;

import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.exception.AuthErrorCode;
import com.coze.openapi.client.exception.CozeAuthException;
import com.coze.openapi.service.auth.WebOAuthClient;
import com.coze.openapi.service.config.Consts;

/*
 * Using web oauth as example, show how to handle the Auth exception
 *
 */
public class HandlerExceptionExample {
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
     * oauthURL = oauth.getOAuthURL(redirectURI, null, "workspaceID");
     * */

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
    try {
      OAuthToken resp = oauth.getAccessToken(code, redirectURI);
      System.out.println(resp);
    } catch (CozeAuthException e) {
      /*
       * The SDK has enumerated existing authentication error codes
       * You need to handle the exception and guide users to re-authenticate
       * For different oauth type, the error code may be different, you should read document to get more information
       */
      if (AuthErrorCode.AUTHORIZATION_PENDING.equals(e.getCode())) {
        // The authorization code is invalid or expired, please re-authorize
      }

      System.out.println(e.getMessage());
    }
  }
}
