package example.chat;

import com.coze.openapi.client.chat.CancelChatReq;
import com.coze.openapi.client.chat.ChatReq;
import com.coze.openapi.client.chat.RetrieveChatReq;
import com.coze.openapi.client.chat.message.ListAllMessageReq;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.chat.model.ChatStatus;
import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.CreateConversationResp;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Arrays;
import java.util.List;

public class ChatExample {

    // 非流式接口的聊天，需要先创建聊天，然后轮询聊天结果
    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String botID = System.getenv("BOT_ID");
        String uid = System.getenv("USER_ID");

        CreateConversationResp conversationResp = coze.conversations().create(CreateConversationReq.builder()
                .messages(Arrays.asList(Message.buildUserQuestionText("你好"))).build());
        String conversationID = conversationResp.getId();

        ChatReq req = ChatReq.builder()
                             .conversationID(conversationID)
                             .botID(botID)
                             .userID(uid)
                             .messages(Arrays.asList(Message.buildUserQuestionText("你好")))
                             .build();

        // 创建聊天
        Chat resp = coze.chats().create(req);
        System.out.println(resp);
        String chatID = resp.getId();

        Integer count = 1;
        while (true) {
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resp = coze.chats().retrieve(RetrieveChatReq.of(conversationID, chatID));
            System.out.println(resp);
            if (ChatStatus.COMPLETED.equals(resp.getStatus())) {
                break;
            }
            if (count >= 2) {
                // 如果聊天长时间没有完成，可以取消聊天
                Chat cancelResp = coze.chats().cancel(CancelChatReq.of(conversationID, chatID));
                System.out.println(cancelResp);


                resp = coze.chats().retrieve(RetrieveChatReq.of(conversationID, chatID));
                System.out.println(resp);
                break;
            }
        }

        List<Message> msg = coze.chats().message().listAll(ListAllMessageReq.of(conversationID, chatID));
        for (Message message : msg) {
            System.out.println(message);
        }
    }
} 