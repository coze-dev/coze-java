package example.conversation.message;

import com.coze.openapi.client.connversations.message.DeleteMessageReq;
import com.coze.openapi.client.connversations.message.RetrieveMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class MessageDeleteExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String conversationID = System.getenv("CONVERSATION_ID");
        String msgID = System.getenv("MESSAGE_ID");

        RetrieveMessageReq req = RetrieveMessageReq.builder()
                                                   .conversationID(conversationID)
                                                   .messageID(msgID)
                                                   .build();
        Message message = coze.conversations().messages().retrieve(req);
        System.out.println(message);

        try {
            DeleteMessageReq.DeleteMessageReqBuilder builder = DeleteMessageReq.builder();
            builder.conversationID(conversationID)
                   .messageID(msgID);
            Message resp = coze.conversations().messages().delete(builder.build());
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 