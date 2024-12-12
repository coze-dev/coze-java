/* (C)2024 */
package example.workflow;

import java.util.HashMap;
import java.util.Map;

import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.RunWorkflowResp;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

/*
This example describes how to use the workflow interface to chat.
* */
public class RunWorkflowExample {

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
    ;

    String workflowID = System.getenv("WORKFLOW_ID");

    // if your workflow need input params, you can send them by map
    Map<String, Object> data = new HashMap<>();
    data.put("param name", "param values");
    RunWorkflowReq req = RunWorkflowReq.builder().workflowID(workflowID).parameters(data).build();

    RunWorkflowResp resp = coze.workflows().runs().create(req);
    System.out.println(resp);
  }
}
