package example.auth;

/*
This example is about how to use the service jwt oauth process to acquire user authorization.

# Firstly, users need to access https://www.coze.com/open/oauth/apps. For the cn environment,
# users need to access https://www.coze.cn/open/oauth/apps to create an OAuth App of the type
# of Service application.
# The specific creation process can be referred to in the document:
# https://www.coze.com/docs/developer_guides/oauth_jwt. For the cn environment, it can be
# accessed at https://www.coze.cn/docs/developer_guides/oauth_jwt.
# After the creation is completed, the client ID, private key, and public key id, can be obtained.
# For the client secret and public key id, users need to keep it securely to avoid leakage.
* */
import com.coze.openapi.client.auth.GetAccessTokenResp;
import com.coze.openapi.client.auth.scope.Scope;
import com.coze.openapi.client.exception.CozeAuthException;
import com.coze.openapi.service.auth.JWTOAuthClient;
import com.coze.openapi.service.auth.JWTOauth;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.CozeAPI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class JWTsOauthExample {

    public static void main(String[] args) {

        // The default access is api.coze.com, but if you need to access api.coze.cn,
        // please use base_url to configure the api endpoint to access
        String cozeAPIBase = System.getenv("COZE_API_BASE");
        if(cozeAPIBase==null|| cozeAPIBase.isEmpty()){
            cozeAPIBase = Consts.COZE_COM_BASE_URL;
        }
        String jwtOauthClientId = System.getenv("COZE_JWT_OAUTH_CLIENT_ID");
        String jwtOauthPrivateKey = System.getenv("COZE_JWT_OAUTH_PRIVATE_KEY");
        String jwtOauthPrivateKeyFilePath = System.getenv("COZE_JWT_OAUTH_PRIVATE_KEY_FILE_PATH");
        String jwtOauthPublicKeyId = System.getenv("COZE_JWT_OAUTH_PUBLIC_KEY_ID");

        JWTOAuthClient oauth = null;
        try {
            jwtOauthPrivateKey = new String(Files.readAllBytes(Paths.get(jwtOauthPrivateKeyFilePath)), StandardCharsets.UTF_8);
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
            oauth = new JWTOAuthClient(jwtOauthClientId, jwtOauthPrivateKey, jwtOauthPublicKeyId, cozeAPIBase);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            GetAccessTokenResp resp = oauth.getAccessToken();
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        The jwt oauth process does not support refreshing tokens. When the token expires,
        just directly call get_access_token to generate a new token.
        * */
        CozeAPI coze = new CozeAPI(new JWTOauth(oauth));
        // you can also specify the scope and session for it

        JWTOauth jwtOauth = JWTOauth.builder().
                scope(Scope.buildBotChat(Arrays.asList("botID"), Arrays.asList("permissions"))).
                sessionName("sessionName").build();
        CozeAPI cozes = new CozeAPI(jwtOauth);
    }
} 