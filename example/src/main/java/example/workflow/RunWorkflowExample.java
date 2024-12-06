package example.workflow;

import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.RunWorkflowResp;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.HashMap;
import java.util.Map;


/*
This example describes how to use the workflow interface to chat.
* */
public class RunWorkflowExample {

    public static void main(String[] args) {
        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE_URL"))
                .auth(authCli)
                .readTimeout(10000)
                .build();;

        String workflowID = System.getenv("WORKSPACE_ID");

        // if your workflow need input params, you can send them by map
        Map<String, Object> data = new HashMap<>();
        data.put("param name", "param values");
        RunWorkflowReq.RunWorkflowReqBuilder builder = RunWorkflowReq.builder();
        builder.workflowID(workflowID).parameters(data);

        RunWorkflowResp resp = coze.workflows().runs().create(builder.build());
        System.out.println(resp);

    }
} 