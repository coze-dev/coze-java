package example.chat;

import com.coze.openapi.client.chat.ChatReq;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import io.reactivex.Flowable;

import java.util.Arrays;

public class StreamChatExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String botID = System.getenv("BOT_ID");
        String uid = System.getenv("USER_ID");
        String conversationID = System.getenv("CONVERSATION_ID");

        ChatReq req = ChatReq.builder()
                             .conversationID(conversationID)
                             .botID(botID)
                             .userID(uid)
                             .messages(Arrays.asList(Message.buildUserQuestionText("你好")))
                             .build();

        Flowable<ChatEvent> resp = coze.chat().stream(req);
        resp.blockingForEach(event -> {
            if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
                System.out.print(event.getMessage().getContent());
            } else {
                System.out.println("=============== event ===============");
                System.out.println(event);
                System.out.println("=============== event ===============");
            }
            coze.shutdownExecutor();
        });
    }
} 