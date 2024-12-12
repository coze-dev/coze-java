package example.auth;

import com.coze.openapi.client.auth.GetAccessTokenResp;
import com.coze.openapi.client.exception.CozeAuthException;
import com.coze.openapi.service.auth.DeviceOAuthClient;

public class DevicesOAuthExample {

    public static void main(String[] args) {
        String clientID = System.getenv("CLIENT_ID");
        DeviceOAuthClient oAuthBase = new DeviceOAuthClient(clientID);
        String deviceCode = System.getenv("DEVICE_CODE");
        
        try {
            GetAccessTokenResp resp = oAuthBase.getAccessToken(deviceCode);
            System.out.println(resp);
        } catch (CozeAuthException e) {
            switch (e.getCode()) {
                case AuthorizationPending:
            }
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 