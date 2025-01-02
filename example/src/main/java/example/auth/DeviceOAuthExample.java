package example.auth;

import com.coze.openapi.client.auth.DeviceAuthResp;
import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.exception.AuthErrorCode;
import com.coze.openapi.client.exception.CozeAuthException;
import com.coze.openapi.service.auth.DeviceOAuthClient;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.CozeAPI;

/*
This example is about how to use the device oauth process to acquire user authorization.

Firstly, users need to access https://www.coze.com/open/oauth/apps. For the cn environment,
users need to access https://www.coze.cn/open/oauth/apps to create an OAuth App of the type
of TVs/Limited Input devices/Command line programs.
The specific creation process can be referred to in the document:
https://www.coze.com/docs/developer_guides/oauth_device_code. For the cn environment, it can be
accessed at https://www.coze.cn/docs/developer_guides/oauth_device_code.
After the creation is completed, the client ID can be obtained.
* */
public class DeviceOAuthExample {

  public static void main(String[] args) {
    String clientID = System.getenv("COZE_DEVICE_OAUTH_CLIENT_ID");

    /*
     * The default access is api.coze.com, but if you need to access api.coze.cn,
     * please use base_url to configure the api endpoint to access
     */
    String cozeAPIBase = System.getenv("COZE_API_BASE");
    if (cozeAPIBase == null || cozeAPIBase.isEmpty()) {
      cozeAPIBase = Consts.COZE_COM_BASE_URL;
    }

    DeviceOAuthClient oauth =
        new DeviceOAuthClient.DeviceOAuthBuilder().clientID(clientID).baseURL(cozeAPIBase).build();

    /*
    In the device oauth authorization process, developers need to first call the interface
    of Coze to generate the device code to obtain the user_code and device_code. Then generate
    the authorization link through the user_code, guide the user to open the link, fill in the
    user_code, and consent to the authorization. Developers need to call the interface of Coze
    to generate the token through the device_code. When the user has not authorized or rejected
    the authorization, the interface will throw an error and return a specific error code.
    After the user consents to the authorization, the interface will succeed and return the
    access_token.


    First, make a call to obtain 'getDeviceCode'
    */

    DeviceAuthResp codeResp = oauth.getDeviceCode();
    System.out.println(codeResp);

    /*
     * The space permissions for which the Access Token is granted can be specified. As following codes:
     * DeviceAuthResp wCodeResp = oauth.getDeviceCode("workspaceID");
     * */

    /*
    The returned device_code contains an authorization link. Developers need to guide users
    to open up this link.
    open codeResp.getVerificationUri
    * */

    System.out.println("Please open url: " + codeResp.getVerificationURL());

    /*
    The developers then need to use the device_code to poll Coze's interface to obtain the token.
    The SDK has encapsulated this part of the code in and handled the different returned error
    codes. The developers only need to invoke getAccessToken.
    * */
    try {
      /*
       * if the developers set poll as true, the sdk will automatically handle pending and slow down exception
       * */
      OAuthToken resp = oauth.getAccessToken(codeResp.getDeviceCode(), true);
      System.out.println(resp);

      // use the access token to init Coze client
      CozeAPI coze =
          new CozeAPI.Builder()
              .auth(new TokenAuth(resp.getAccessToken()))
              .baseURL(cozeAPIBase)
              .build();
      // When the token expires, you can also refresh and re-obtain the token
      resp = oauth.refreshToken(resp.getRefreshToken());
    } catch (CozeAuthException e) {
      if (AuthErrorCode.ACCESS_DENIED.equals(e.getCode())) {
        /*
        The user rejected the authorization.
        Developers need to guide the user to open the authorization link again.
        * */
        System.out.println("access denied");
      } else if (AuthErrorCode.EXPIRED_TOKEN.equals(e.getCode())) {
        /*
        The token has expired. Developers need to guide the user to open
        the authorization link again.
        * */
        System.out.println("expired token");
      } else {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
