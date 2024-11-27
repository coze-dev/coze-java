package example.bot;

import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class BotInfoExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String botID = System.getenv("BOT_ID");

        try {
            Bot botInfo = coze.bots().retrieve(botID);
            System.out.println(botInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 