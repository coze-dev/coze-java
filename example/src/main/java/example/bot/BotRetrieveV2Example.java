package example.bot;

import java.io.IOException;
import java.util.Iterator;

import com.coze.openapi.client.bots.ListBotV2Req;
import com.coze.openapi.client.bots.RetrieveBotV2Req;
import com.coze.openapi.client.bots.RetrieveBotV2Resp;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.bots.model.BotSimpleInfo;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

/*
This example is for describing how to retrieve a bot, fetch published bot list from the API.
The document for those interface:
* */
public class BotRetrieveV2Example {
  public static void main(String[] args) throws IOException {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    String botID = System.getenv("COZE_BOT_ID");
    if (token == null || token.isEmpty()) {
      throw new IllegalArgumentException("COZE_API_TOKEN environment variable is required");
    }
    if (botID == null || botID.isEmpty()) {
      throw new IllegalArgumentException("COZE_BOT_ID environment variable is required");
    }
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();
    ;

    /*
     * retrieve a bot
     * */
    RetrieveBotV2Resp botInfo = coze.bots().retrieve(RetrieveBotV2Req.of(botID,null));
    Bot bot = botInfo.getBot();
    System.out.println(bot);
    System.out.println(botInfo.getLogID());

    /*
     * get published bot list
     * */

    Integer pageNum = 1;
    String workspaceID = System.getenv("WORKSPACE_ID");
    ListBotV2Req listBotReq =
        ListBotV2Req.builder().workspaceID(workspaceID).pageNum(pageNum).pageSize(10).build();
    PageResp<BotSimpleInfo> botList = coze.bots().list(listBotReq);

    // 2. The SDK encapsulates an iterator, which can be used to turn pages backward automatically.
    Iterator<BotSimpleInfo> iterator = botList.getIterator();
    while (iterator.hasNext()) {
      iterator.forEachRemaining(System.out::println);
    }

    // the page result will return followed information
    System.out.println("total: " + botList.getTotal());
    System.out.println("has_more: " + botList.getHasMore());
    System.out.println("logID: " + botList.getLogID());
    for (BotSimpleInfo item : botList.getItems()) {
      System.out.println(item);
    }
  }
}
