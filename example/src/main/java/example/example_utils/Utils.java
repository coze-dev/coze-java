package example.example_utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.coze.openapi.service.auth.JWTOAuth;
import com.coze.openapi.service.auth.JWTOAuthClient;
import com.coze.openapi.service.service.CozeAPI;

public class Utils {

  public static CozeAPI getCozeAPI() {
    // The default access is api.coze.com, but if you need to access api.coze.cn,
    // please use base_url to configure the api endpoint to access
    String cozeAPIBase = System.getenv("COZE_API_BASE");
    if (cozeAPIBase == null || cozeAPIBase.isEmpty()) {
      cozeAPIBase = "api.coze.cn";
    }
    String jwtOauthClientID = System.getenv("COZE_JWT_OAUTH_CLIENT_ID");
    String jwtOauthPrivateKey = System.getenv("COZE_JWT_OAUTH_PRIVATE_KEY");
    String jwtOauthPrivateKeyFilePath = System.getenv("COZE_JWT_OAUTH_PRIVATE_KEY_FILE_PATH");
    String jwtOauthPublicKeyID = System.getenv("COZE_JWT_OAUTH_PUBLIC_KEY_ID");
    JWTOAuthClient oauth = null;
    try {
      jwtOauthPrivateKey =
          new String(
              Files.readAllBytes(Paths.get(jwtOauthPrivateKeyFilePath)), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }

    /*
    The jwt oauth type requires using private to be able to issue a jwt token, and through
    the jwt token, apply for an access_token from the coze service. The sdk encapsulates
    this procedure, and only needs to use get_access_token to obtain the access_token under
    the jwt oauth process.
    Generate the authorization token
    The default ttl is 900s, and developers can customize the expiration time, which can be
    set up to 24 hours at most.
    * */
    try {
      oauth =
          new JWTOAuthClient.JWTOAuthBuilder()
              .clientID(jwtOauthClientID)
              .privateKey(jwtOauthPrivateKey)
              .publicKey(jwtOauthPublicKeyID)
              .baseURL(cozeAPIBase)
              .build();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    return new CozeAPI.Builder()
        .auth(JWTOAuth.builder().jwtClient(oauth).build())
        .baseURL(cozeAPIBase)
        .build();
  }
}
