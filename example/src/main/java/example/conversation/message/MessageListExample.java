package example.conversation.message;

import com.coze.openapi.client.connversations.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Iterator;

public class MessageListExample {

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String conversationID = System.getenv("CONVERSATION_ID");
        Integer pageSize = 2;

        try {
            ListMessageReq.ListMessageReqBuilder builder = ListMessageReq.builder();
            builder.conversationID(conversationID).limit(pageSize);

            PageResult<Message> resp = coze.conversations().message().list(builder.build());
            Iterator<Message> iterator = resp.getIterator();
            while (iterator.hasNext()) {
                Message message = iterator.next();
                System.out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 