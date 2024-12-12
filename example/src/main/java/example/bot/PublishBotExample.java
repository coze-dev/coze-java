package example.bot;

import com.coze.openapi.client.bots.PublishBotResp;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class PublishBotExample {

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String botID = System.getenv("BOT_ID");

        PublishBotResp resp = coze.bots().publish(botID);
        System.out.println(resp);
    }
} 