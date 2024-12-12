package example.auth;

import com.coze.openapi.client.auth.GetAccessTokenResp;
import com.coze.openapi.client.exception.CozeAuthException;
import com.coze.openapi.service.auth.JWTOAuthClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class JWTsOauthExample {

    public static void main(String[] args) {
        String clientID = System.getenv("CLIENT_ID");
        String privateKeyPath = System.getenv("PRIVATE_KEY_PATH");
        String privateKeyContent = "";
        try {
            privateKeyContent = new String(Files.readAllBytes(Paths.get(privateKeyPath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String publicKey = System.getenv("PUBLIC_KEY");

        JWTOAuthClient oAuthBase = null;
        try {
            oAuthBase = new JWTOAuthClient(clientID, privateKeyContent, publicKey);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            GetAccessTokenResp resp = oAuthBase.getAccessToken();
            System.out.println(resp);
        } catch (CozeAuthException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 