package example.chat;

import com.coze.openapi.client.chat.*;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.chat.model.ChatPoll;
import com.coze.openapi.client.chat.model.ChatStatus;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/*
This example describes how to use the chat interface to initiate conversations,
poll the status of the conversation, and obtain the messages after the conversation is completed.
* */
public class ChatExample {

    // For non-streaming chat API, it is necessary to create a chat first and then poll the chat results.
    public static void main(String[] args) throws Exception {
        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        String botID = System.getenv("PUBLISHED_BOT_ID");
        String uid = System.getenv("USER_ID");

        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .readTimeout(10000)
                .build();


        /*
        * Step one, create chat
        * Call the coze.chat().create() method to create a chat. The create method is a non-streaming
        * chat and will return a Chat class. Developers should periodically check the status of the
        * chat and handle them separately according to different states.
        * */
        CreateChatReq req = CreateChatReq.builder()
                             .botID(botID)
                             .userID(uid)
                             .messages(Collections.singletonList(Message.buildUserQuestionText("What can you do?")))
                             .build();

        CreateChatResp chatResp = coze.chat().create(req);
        System.out.println(chatResp);
        Chat chat = chatResp.getChat();
        // get chat id and conversationID
        String chatID = chat.getID();
        String conversationID = chat.getConversationID();

        /*
        * Step two, poll the result of chat
        * Assume the development allows at most one chat to run for 10 seconds. If it exceeds 10 seconds,
        * the chat will be cancelled.
        * And when the chat status is not completed, poll the status of the chat once every second.
        * After the chat is completed, retrieve all messages in the chat.
        * */
        long timeout = 10L;
        long start = System.currentTimeMillis() / 1000;
        while (ChatStatus.IN_PROGRESS.equals(chat.getStatus())) {
            try {
                // The API has a rate limit with 1 QPS.
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            if ((System.currentTimeMillis() / 1000) - start > timeout) {
                // The chat can be cancelled before its completed.
                System.out.println(coze.chat().cancel(CancelChatReq.of(conversationID, chatID)));
                break;
            }
            RetrieveChatResp resp = coze.chat().retrieve(RetrieveChatReq.of(conversationID, chatID));
            System.out.println(resp);
            chat = resp.getChat();
            if (ChatStatus.COMPLETED.equals(chat.getStatus())) {
                break;
            }
        }


        // The sdk provide an automatic polling method.
        ChatPoll chat2 = coze.chat().createAndPoll(req);
        System.out.println(chat2);
        // the developer can also set the timeout.
        ChatPoll chat3 = coze.chat().createAndPoll(req, timeout);
        System.out.println(chat3);
    }
} 