package example.conversation;

import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.connversations.ListConversationReq;
import com.coze.openapi.client.connversations.model.Conversation;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import java.util.Iterator;

public class ListConversationsExample {

    public static void main(String[] args) {
        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        String botID = System.getenv("BOT_ID");

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .readTimeout(10000)
                .build();;

        // you can use iterator to automatically retrieve next page
        PageResp<Conversation> conversations = coze.conversations().list(ListConversationReq.of(botID));
        Iterator<Conversation> iter = conversations.getIterator();
        iter.forEachRemaining(System.out::println);


         // the page result will return followed information
        System.out.println("total: " + conversations.getTotal());
        System.out.println("has_more: " + conversations.getHasMore());
        System.out.println("logID: " + conversations.getLogID());
        for (Conversation item : conversations.getItems()) {
            System.out.println(item);
        }
    }
}
