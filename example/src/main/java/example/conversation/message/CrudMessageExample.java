package example.conversation.message;

import com.coze.openapi.client.connversations.message.CreateMessageReq;
import com.coze.openapi.client.connversations.message.DeleteMessageReq;
import com.coze.openapi.client.connversations.message.RetrieveMessageReq;
import com.coze.openapi.client.connversations.message.UpdateMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.client.connversations.message.model.MessageRole;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class CrudMessageExample {

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

        /*
        * create message to specific conversation
        * */
        CreateMessageReq.CreateMessageReqBuilder builder = CreateMessageReq.builder();
        builder.conversationID(conversationID)
                .role(MessageRole.USER)
                .content("message count")
                .contentType(MessageContentType.TEXT);
        Message message = coze.conversations().messages().create(builder.build());
        System.out.println(message);

        /*
        * retrieve message
        * */
        Message retrievedMsg = coze.conversations().messages().retrieve(RetrieveMessageReq.builder()
                .conversationID(conversationID)
                .messageID(message.getId())
                .build());
        System.out.println(retrievedMsg);

        /*
        * update message
        * */
        UpdateMessageReq updateReq = UpdateMessageReq.builder()
                .conversationID(conversationID).messageID(message.getId())
                .content(String.format("modified message content:%s", message.getContent()))
                .contentType(MessageContentType.TEXT).build();
        Message resp = coze.conversations().messages().update(updateReq);
        System.out.println(resp);

        /*
        * delete message
        * */
        Message deletedMsg = coze.conversations().messages().delete(DeleteMessageReq.of(conversationID, message.getId()));
        System.out.println(deletedMsg);

    }
} 