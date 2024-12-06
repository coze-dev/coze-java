package example.conversation;

import com.coze.openapi.client.connversations.ClearConversationReq;
import com.coze.openapi.client.connversations.ClearConversationResp;
import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.CreateConversationResp;
import com.coze.openapi.client.connversations.RetrieveConversationReq;
import com.coze.openapi.client.connversations.RetrieveConversationResp;
import com.coze.openapi.client.connversations.message.CreateMessageReq;
import com.coze.openapi.client.connversations.message.CreateMessageResp;
import com.coze.openapi.client.connversations.message.model.MessageObjectString;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Arrays;

/*
* This example is about how to create conversation and retrieve.
* */
public class CreateConversationExample {

    public static void main(String[] args) {
        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE_URL"))
                .auth(authCli)
                .readTimeout(10000)
                .build();;

        CreateConversationResp resp = coze.conversations().create(new CreateConversationReq());
        System.out.println("create conversations" + resp);

        String conversationID = resp.getConversation().getId();
        RetrieveConversationResp getResp = coze.conversations().retrieve(RetrieveConversationReq.of(conversationID));
        System.out.println("retrieve conversations:" + getResp);

        // you can manually create message for conversation
        CreateMessageResp msgs = coze.conversations().messages().create(CreateMessageReq
                .builder()
                .conversationID(conversationID)
                // if you want to create object content, you can use followed method to simplify your code
                .objectContent(
                        Arrays.asList(MessageObjectString.buildText("hello"),
                                MessageObjectString.buildImageByURL(System.getenv("PICTURE_URL")),
                                MessageObjectString.buildFileByURL(System.getenv("FILE_URL"))))
                .build());
        System.out.println(msgs);

        ClearConversationResp clearResp = coze.conversations().clear(ClearConversationReq.of(conversationID));
        System.out.println(clearResp);
    }
} 