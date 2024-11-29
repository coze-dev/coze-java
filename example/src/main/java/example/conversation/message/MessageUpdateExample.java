package example.conversation.message;

import com.coze.openapi.client.connversations.message.UpdateMessageReq;
import com.coze.openapi.client.connversations.message.RetrieveMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class MessageUpdateExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String conversationID = System.getenv("CONVERSATION_ID");
        String msgID = System.getenv("MESSAGE_ID");

        // 获取原消息
        RetrieveMessageReq req = RetrieveMessageReq.builder()
                                                   .conversationID(conversationID)
                                                   .messageID(msgID)
                                                   .build();
        Message message = coze.conversations().messages().retrieve(req);
        System.out.println(message);

        // 修改消息
        try {
            UpdateMessageReq.UpdateMessageReqBuilder builder = UpdateMessageReq.builder();
            builder.conversationID(conversationID).messageID(msgID)
                   .content(String.format("modified message content:%s", message.getContent()))
                   .contentType(MessageContentType.TEXT);
            Message resp = coze.conversations().messages().update(builder.build());
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 获取修改后的消息
        message = coze.conversations().messages().retrieve(req);
        System.out.println(message);
    }
} 