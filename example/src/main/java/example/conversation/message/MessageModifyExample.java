package example.conversation.message;

import com.coze.openapi.client.connversations.message.UpdateMessageReq;
import com.coze.openapi.client.connversations.message.RetrieveMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class MessageModifyExample {

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
        System.out.println("=============== origin message ===============");
        System.out.println(message);
        System.out.println("=============== origin message ===============");

        try {
            UpdateMessageReq.UpdateMessageReqBuilder builder = UpdateMessageReq.builder();
            builder.conversationID(conversationID).messageID(msgID)
                   .content(String.format("modified message content:%s", message.getContent()))
                   .contentType(MessageContentType.TEXT);
            Message resp = coze.conversations().message().update(builder.build());
            System.out.println("=============== modified message ===============");
            System.out.println(resp);
            System.out.println("=============== modified message ===============");
        } catch (Exception e) {
            System.out.println("=============== modified message error ===============");
            e.printStackTrace();
            System.out.println("=============== modified message error ===============");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        message = coze.conversations().message().retrieve(req);
        System.out.println("=============== retrieved modified message ===============");
        System.out.println(message);
        System.out.println("=============== retrieved modified message ===============");
    }
} 