/* (C)2024 */
package example.auth;

import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.CozeAPI;

/*
How to use personal access token to init Coze client.

Firstly, you need to access https://www.coze.com/open/oauth/pats (for the cn environment,
visit https://www.coze.cn/open/oauth/pats).

Click to add a new token. After setting the appropriate name, expiration time, and
permissions, click OK to generate your personal access token. Please store it in a
secure environment to prevent this personal access token from being disclosed.
*/
public class TokenAuthExample {
  public static void main(String[] args) {
    String cozeAPIToken = System.getenv("COZE_API_TOKEN");

    /*
     * The default access is api.coze.com, but if you need to access api.coze.cn,
     * please use base_url to configure the api endpoint to access
     */
    String cozeAPIBase = System.getenv("COZE_API_BASE");
    if (cozeAPIBase == null || cozeAPIBase.isEmpty()) {
      cozeAPIBase = Consts.COZE_COM_BASE_URL;
    }

    /*
     *
     * The Coze SDK offers the TokenAuth class for constructing an Auth class based on a fixed
     * access token. Meanwhile, the Coze class enables the passing in of an Auth class to build
     * a coze client.
     *
     * Therefore, you can utilize the following code to initialize a coze client.
     *
     * */
    CozeAPI coze =
        new CozeAPI.Builder().auth(new TokenAuth(cozeAPIToken)).baseURL(cozeAPIBase).build();
  }
}
