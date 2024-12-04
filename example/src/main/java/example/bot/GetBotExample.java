package example.bot;

import com.coze.openapi.client.bots.ListBotReq;
import com.coze.openapi.client.bots.RetrieveBotReq;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.bots.model.SimpleBot;
import com.coze.openapi.client.common.pagination.PageResult;
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
        Bot botInfo = coze.bots().retrieve(RetrieveBotReq.of(botID));

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
        PageResult<SimpleBot> botList = coze.bots().list(listBotReq);

        // the api provides two ways for developers to turn pages for all paging interfaces.
        // 1. The first way is to let developers manually call the API to request the next page.
        while (botList.getHasMore()){
            pageNum++;
            listBotReq.setPageNum(pageNum);
            botList = coze.bots().list(listBotReq);
        }

        // 2. The SDK encapsulates an iterator, which can be used to turn pages backward automatically.
        Iterator<SimpleBot> iterator = botList.getIterator();
        while (iterator.hasNext()) {
            iterator.forEachRemaining(System.out::println);
        }
    }
}
