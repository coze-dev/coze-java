package example.conversation.message;

import com.coze.openapi.client.connversations.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Iterator;

public class ListMessageExample {

    public static void main(String[] args) {
        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE_URL"))
                .auth(authCli)
                .readTimeout(10000)
                .build();

        String conversationID = System.getenv("CONVERSATION_ID");
        Integer limit = 2;

        ListMessageReq req = ListMessageReq.builder()
                .conversationID(conversationID)
                .limit(limit)
                .build();

        // you can use iterator to automatically retrieve next page
        PageResult<Message> messages = coze.conversations().messages().list(req);
        Iterator<Message> iter = messages.getIterator();
        iter.forEachRemaining(System.out::println);

        // you can manually retrieve next page
        int pageNum = 1;
        while (messages.getHasMore()){
            pageNum++;
            req.setLimit(pageNum);
            messages = coze.conversations().messages().list(req);
            for (Message item : messages.getItems()) {
                System.out.println(item);
            }
        }
    }
} 