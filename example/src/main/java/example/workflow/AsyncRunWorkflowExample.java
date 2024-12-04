package example.workflow;

import com.coze.openapi.client.workflows.run.RetrieveRunHistoryReq;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.RunWorkflowResp;
import com.coze.openapi.client.workflows.run.model.WorkflowRunHistory;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AsyncRunWorkflowExample {
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

        RunWorkflowReq req = RunWorkflowReq.builder()
                .workflowID(workflowID)
                .parameters(data)
                // if you want the workflow run asynchronously, you must set isAsync to true.
                .isAsync(true)
                .build();

        /*
        Call the  coze.workflows().runs().run() method to create a workflow run. The create method
        is a non-streaming chat and will return a WorkflowRunResult class.
        * */
        RunWorkflowResp resp = coze.workflows().runs().run(req);
        System.out.println("Start async workflow run:" + resp.getExecuteID());

        String executeID = resp.getExecuteID();
        boolean isFinished = false;

        while(!isFinished){
            WorkflowRunHistory historyResp = coze.workflows().runs().history().retrieve(RetrieveRunHistoryReq.of(workflowID, executeID));
            switch (historyResp.getExecuteStatus()){
                case FAIL:
                    System.out.println("Workflow run failed, reason:" + historyResp.getErrorMessage());
                    isFinished = true;
                    break;
                case RUNNING:
                    System.out.println("Workflow run is running");
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Workflow run success" + historyResp.getOutput());
                    isFinished = true;
                    break;
            }
        }
    }
}
