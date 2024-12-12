/* (C)2024 */
package example.conversation.message;

import java.util.Iterator;

import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.connversations.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class MessageListExample {

  public static void main(String[] args) {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();

    String conversationID = System.getenv("CONVERSATION_ID");
    Integer limit = 2;

    ListMessageReq req =
        ListMessageReq.builder().conversationID(conversationID).limit(limit).build();

    // you can use iterator to automatically retrieve next page
    PageResp<Message> messages = coze.conversations().messages().list(req);
    Iterator<Message> iter = messages.getIterator();
    iter.forEachRemaining(System.out::println);

    // the page result will return followed information
    System.out.println("total: " + messages.getTotal());
    System.out.println("has_more: " + messages.getHasMore());
    System.out.println("lastID: " + messages.getLastID());
    System.out.println("logID: " + messages.getLogID());
    for (Message item : messages.getItems()) {
      System.out.println(item);
    }
  }
}
