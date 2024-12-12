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

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
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

        Chat resp = coze.chats().create(req);
        System.out.println("=============== chat ===============");
        System.out.println(resp);
        System.out.println("=============== chat ===============");
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
            System.out.println("=============== retrieved chat ===============");
            System.out.println(resp);
            System.out.println("=============== retrieved chat ===============");
            if (ChatStatus.COMPLETED.equals(resp.getStatus())) {
                System.out.println("=============== chat completed ===============");
                break;
            }
            if (count >= 2) {
                System.out.println("=============== cancel chat ===============");
                Chat cancelResp = coze.chats().cancel(CancelChatReq.of(conversationID, chatID));
                System.out.println(cancelResp);
                System.out.println("=============== cancel chat ===============");


                resp = coze.chats().retrieve(RetrieveChatReq.of(conversationID, chatID));
                System.out.println(resp);
                break;
            }
        }

        List<Message> msg = coze.chats().message().listAll(ListAllMessageReq.of(conversationID, chatID));
        count = 0;
        for (Message message : msg) {
            count++;
            System.out.println("=============== " + count + " ===============");
            System.out.println(message);
        }
    }
} 