package example.conversation.message;

import com.coze.openapi.client.connversations.message.CreateMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.client.connversations.message.model.MessageRole;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class MessageExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String conversationID = System.getenv("CONVERSATION_ID");

        CreateMessageReq.CreateMessageReqBuilder builder = CreateMessageReq.builder();
        builder.conversationID(conversationID)
               .role(MessageRole.USER)
               .content("message count")
               .contentType(MessageContentType.TEXT);
        try {
            Message resp = coze.conversations().message().create(builder.build());
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 