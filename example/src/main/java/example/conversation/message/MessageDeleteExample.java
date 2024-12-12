package example.conversation.message;

import com.coze.openapi.client.connversations.message.DeleteMessageReq;
import com.coze.openapi.client.connversations.message.RetrieveMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class MessageDeleteExample {

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String conversationID = System.getenv("CONVERSATION_ID");
        String msgID = System.getenv("MESSAGE_ID");

        RetrieveMessageReq req = RetrieveMessageReq.builder()
                                                   .conversationID(conversationID)
                                                   .messageID(msgID)
                                                   .build();
        Message message = coze.conversations().message().retrieve(req);

        try {
            DeleteMessageReq.DeleteMessageReqBuilder builder = DeleteMessageReq.builder();
            builder.conversationID(conversationID)
                   .messageID(msgID);
            Message resp = coze.conversations().message().delete(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 