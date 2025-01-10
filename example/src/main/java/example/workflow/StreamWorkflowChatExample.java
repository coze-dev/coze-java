package example.workflow;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.workflows.chat.WorkflowChatReq;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import io.reactivex.Flowable;

public class StreamWorkflowChatExample {
  public static void main(String[] args) {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();

    String workflowID = System.getenv("WORKFLOW_ID");
    String appID = System.getenv("COZE_APP_ID");
    /*
     * stream workflow chat
     */
    Map<String, Object> data = new HashMap<>();
    data.put("param name", "param values");
    WorkflowChatReq req =
        WorkflowChatReq.builder()
            .workflowID(workflowID)
            .appID(appID)
            .parameters(data)
            .additionalMessages(
                Collections.singletonList(Message.buildUserQuestionText("who are you")))
            .build();

    Flowable<ChatEvent> events = coze.workflows().chat().stream(req);
    events.blockingForEach(
        event -> {
          if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
            System.out.print(event.getMessage().getContent());
          }
        });
  }
}
