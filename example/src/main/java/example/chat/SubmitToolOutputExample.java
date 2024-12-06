package example.chat;
/*
* This use case teaches you how to use local plugin.
* */
import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.SubmitToolOutputsReq;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.chat.model.ChatToolCall;
import com.coze.openapi.client.chat.model.ToolOutput;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SubmitToolOutputExample {

    /*
    * In this example, we assume that the user asks the bot about today's weather.
    * This scenario requires the invocation of local plugins.
    * */
    public static void main(String[] args) {

        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        String botID = System.getenv("PUBLISHED_BOT_ID");
        String userID = System.getenv("USER_ID");
        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .readTimeout(10000)
                .build();


        CreateChatReq req = CreateChatReq.builder()
                             .botID(botID)
                             .userID(userID)
                             .messages(Collections.singletonList(
                                     Message.buildUserQuestionText("how's the weather in ShenZhen today?")))
                             .build();

        AtomicReference<ChatEvent> pluginEventRef = new AtomicReference<>();
        AtomicReference<String> conversationRef = new AtomicReference<>();
        Flowable<ChatEvent> resp = coze.chat().stream(req);

        resp.blockingForEach(event -> {
            if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
                System.out.print(event.getMessage().getContent());
            }
            /*
             * handle the chat event, if event type is CONVERSATION_CHAT_REQUIRES_ACTION,
             * it means the bot requires the invocation of local plugins.
             * In this example, we will invoke the local plugin to get the weather information.
             * */
            if (ChatEventType.CONVERSATION_CHAT_REQUIRES_ACTION.equals(event.getEvent())) {
                System.out.println("need action");
                pluginEventRef.set(event);
                conversationRef.set(event.getChat().getConversationID());
            }
        });

        String conversationID = conversationRef.get();
        ChatEvent pluginEvent = pluginEventRef.get();

        List<ToolOutput> toolOutputs = new ArrayList<>();
        for (ChatToolCall callInfo : pluginEvent.getChat().getRequiredAction().getSubmitToolOutputs().getToolCalls()) {
            String callID = callInfo.getID();
            // you can handle different plugin by name.
            String functionName = callInfo.getFunction().getName();
            // you should unmarshal arguments if necessary.
            String argsJson = callInfo.getFunction().getArguments();

            toolOutputs.add(ToolOutput.of(callID, "It is 18 to 21"));
        }

        SubmitToolOutputsReq toolReq = SubmitToolOutputsReq.builder()
                .chatID(pluginEvent.getChat().getID())
                .conversationID(conversationID)
                .toolOutputs(toolOutputs)
                .build();


        Flowable<ChatEvent> events = coze.chat().streamSubmitToolOutputs(toolReq);
        events.blockingForEach(event -> {
            if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
                System.out.print(event.getMessage().getContent());
            }
        });
        coze.shutdownExecutor();
    }
} 