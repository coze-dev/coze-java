package example.service;


import com.coze.openapi.client.bots.RetrieveBotReq;
import com.coze.openapi.client.bots.RetrieveBotResp;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class SetRequestTimeoutExample {
    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                // the developer able to set global request timeout when initializing the client
                .readTimeout(1000)
                .connectTimeout(2000)
                .build();


        String botID = System.getenv("COZE_BOT_ID");

        /*
         * retrieve a bot
         * */
        RetrieveBotReq req = RetrieveBotReq
                .builder()
                .botID(botID)
                // when the developer want to request, they are able to sent request timout for any request
                .connectTimeout(1000)
                .readTimeout(3000)
                .build();
        RetrieveBotResp botInfo = coze.bots().retrieve(req);
        Bot bot = botInfo.getBot();
        System.out.println(bot);
        System.out.println(botInfo.getLogID());


    }
}
