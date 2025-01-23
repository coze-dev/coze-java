package example.workflow;

import java.util.HashMap;
import java.util.Map;

import com.coze.openapi.client.workflows.run.ResumeRunReq;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.client.workflows.run.model.WorkflowEventType;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import io.reactivex.Flowable;

/*
This example describes how to use the workflow interface to stream chat.
*/
public class StreamWorkflowExample {

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

    // if your workflow need input params, you can send them by map
    Map<String, Object> data = new HashMap<>();
    data.put("A", "param values");

    RunWorkflowReq req = RunWorkflowReq.builder().workflowID(workflowID).parameters(data).build();

    Flowable<WorkflowEvent> flowable = coze.workflows().runs().stream(req);
    handleEvent(flowable, coze, workflowID);
  }

  /*
   * The stream interface will return an iterator of WorkflowEvent. Developers should iterate
   * through this iterator to obtain WorkflowEvent and handle them separately according to
   * the type of WorkflowEvent.
   */
  private static void handleEvent(Flowable<WorkflowEvent> events, CozeAPI coze, String workflowID) {
    events.subscribe(
        event -> {
          if (event.getEvent().equals(WorkflowEventType.MESSAGE)) {
            System.out.println("Got message" + event.getMessage());
          } else if (event.getEvent().equals(WorkflowEventType.ERROR)) {
            System.out.println("Got error" + event.getError());
          } else if (event.getEvent().equals(WorkflowEventType.DONE)) {
            System.out.println("Got message" + event.getMessage());
          } else if (event.getEvent().equals(WorkflowEventType.INTERRUPT)) {
            handleEvent(
                coze.workflows()
                    .runs()
                    .resume(
                        ResumeRunReq.builder()
                            .workflowID(workflowID)
                            .eventID(event.getInterrupt().getInterruptData().getEventID())
                            .resumeData("your data")
                            .interruptType(event.getInterrupt().getInterruptData().getType())
                            .build()),
                coze,
                workflowID);
          }
        },
        Throwable::printStackTrace);
    coze.shutdownExecutor();
  }
}
