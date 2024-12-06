package example.bot;

import com.coze.openapi.client.bots.ListBotReq;
import com.coze.openapi.client.bots.RetrieveBotReq;
import com.coze.openapi.client.bots.RetrieveBotResp;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.bots.model.SimpleBot;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import java.util.Iterator;

/*
This example is for describing how to retrieve a bot, fetch published bot list from the API.
The document for those interface:
* */
public class GetBotExample {
    public static void main(String[] args) {
        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        String botID = System.getenv("BOT_ID");
        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE_URL"))
                .auth(authCli)
                .readTimeout(10000)
                .build();;

        /*
         * retrieve a bot
         * */
        RetrieveBotResp botInfo = coze.bots().retrieve(RetrieveBotReq.of(botID));
        Bot bot = botInfo.getBot();
        System.out.println(bot);

        /*
         * get published bot list
         * */

        Integer pageNum = 1;
        String workspaceID = System.getenv("WORKSPACE_ID");
        ListBotReq listBotReq = ListBotReq.builder()
                .spaceID(workspaceID)
                .pageNum(pageNum)
                .pageSize(10)
                .build();
        PageResp<SimpleBot> botList = coze.bots().list(listBotReq);

        // 2. The SDK encapsulates an iterator, which can be used to turn pages backward automatically.
        Iterator<SimpleBot> iterator = botList.getIterator();
        while (iterator.hasNext()) {
            iterator.forEachRemaining(System.out::println);
        }

        // the page result will return followed information
        System.out.println("total: " + botList.getTotal());
        System.out.println("has_more: " + botList.getHasMore());
        System.out.println("logID: " + botList.getLogID());
        for (SimpleBot item : botList.getItems()) {
            System.out.println(item);
        }
    }
}
