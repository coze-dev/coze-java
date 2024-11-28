package example.chat;

import com.coze.openapi.client.chat.ChatReq;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.chat.SubmitToolOutputsReq;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.chat.model.ChatToolCall;
import com.coze.openapi.client.chat.model.ToolOutput;
import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.CreateConversationResp;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SubmitToolOutputExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String botID = System.getenv("BOT_ID");
        String uid = System.getenv("USER_ID");

        Message msg = Message.buildUserQuestionText("今天深圳天气如何");
        msg.setBotID(botID);

        CreateConversationResp conversationResp = coze.conversations().create(CreateConversationReq.builder()
                .messages(Arrays.asList(msg)).build());
        String conversationID = conversationResp.getID();

        ChatReq req = ChatReq.builder()
                             .conversationID(conversationID)
                             .botID(botID)
                             .userID(uid)
                             .messages(Arrays.asList(Message.buildUserQuestionText("今天深圳天气如何")))
                             .build();

        AtomicReference<ChatEvent> pluginEventRef = new AtomicReference<>();
        Flowable<ChatEvent> resp = coze.chat().stream(req);
        resp.blockingForEach(event -> {
            if (ChatEventType.CONVERSATION_CHAT_REQUIRES_ACTION.equals(event.getEvent())) {
                pluginEventRef.set(event);
            }
        });

        ChatEvent pluginEvent = pluginEventRef.get();
        System.out.println("=============== plugin event ===============");
        System.out.println(pluginEvent);
        System.out.println("=============== plugin event ===============");

        if (pluginEvent == null) {
            System.out.println("=============== plugin event is null ===============");
            return;
        }

        List<ToolOutput> toolOutputs = new ArrayList<>();
        for (ChatToolCall callInfo : pluginEvent.getChat().getRequiredAction().getSubmitToolOutputs().getToolCalls()) {
            String callID = callInfo.getID();
            toolOutputs.add(ToolOutput.of(callID, "18 度到 21 度"));
        }

        SubmitToolOutputsReq toolReq = SubmitToolOutputsReq.builder()
                .chatID(pluginEvent.getChat().getID())
                .conversationID(conversationID)
                .toolOutputs(toolOutputs)
                .build();

        Chat resp2 = coze.chat().submitToolOutputs(toolReq);
        System.out.println("=============== submit tool outputs ===============");
        System.out.println(resp2);
        System.out.println("=============== submit tool outputs ===============");
    }
} 