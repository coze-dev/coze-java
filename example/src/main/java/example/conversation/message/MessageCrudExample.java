package example.conversation.message;

import com.coze.openapi.client.connversations.message.CreateMessageReq;
import com.coze.openapi.client.connversations.message.CreateMessageResp;
import com.coze.openapi.client.connversations.message.DeleteMessageReq;
import com.coze.openapi.client.connversations.message.DeleteMessageResp;
import com.coze.openapi.client.connversations.message.RetrieveMessageReq;
import com.coze.openapi.client.connversations.message.RetrieveMessageResp;
import com.coze.openapi.client.connversations.message.UpdateMessageReq;
import com.coze.openapi.client.connversations.message.UpdateMessageResp;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.client.connversations.message.model.MessageRole;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class MessageCrudExample {

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

    /*
     * create message to specific conversation
     * */
    CreateMessageReq.CreateMessageReqBuilder builder = CreateMessageReq.builder();
    builder
        .conversationID(conversationID)
        .role(MessageRole.USER)
        .content("message count")
        .contentType(MessageContentType.TEXT);
    CreateMessageResp messageResp = coze.conversations().messages().create(builder.build());
    Message message = messageResp.getMessage();
    System.out.println(message);

    /*
     * retrieve message
     * */
    RetrieveMessageResp retrievedMsgResp =
        coze.conversations()
            .messages()
            .retrieve(
                RetrieveMessageReq.builder()
                    .conversationID(conversationID)
                    .messageID(message.getId())
                    .build());
    Message retrievedMsg = retrievedMsgResp.getMessage();
    System.out.println(retrievedMsg);

    /*
     * update message
     * */
    UpdateMessageReq updateReq =
        UpdateMessageReq.builder()
            .conversationID(conversationID)
            .messageID(message.getId())
            .content(String.format("modified message content:%s", message.getContent()))
            .contentType(MessageContentType.TEXT)
            .build();
    UpdateMessageResp updateResp = coze.conversations().messages().update(updateReq);
    Message updatedMsg = updateResp.getMessage();
    System.out.println(updatedMsg);

    /*
     * delete message
     * */
    DeleteMessageResp deletedMsgResp =
        coze.conversations()
            .messages()
            .delete(DeleteMessageReq.of(conversationID, message.getId()));
    Message deletedMsg = deletedMsgResp.getMessage();
    System.out.println(deletedMsg);
  }
}
