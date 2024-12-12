package example.workflow;

import com.coze.openapi.client.workflows.run.ResumeRunReq;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.client.workflows.run.model.WorkflowEventType;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import io.reactivex.Flowable;

import java.util.HashMap;
import java.util.Map;

public class RunworkExample {

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String wid = System.getenv("WORKFLOW_ID");

        Map<String, Object> data = new HashMap<>();
        data.put("user_input", "今天天气怎么样");
        RunWorkflowReq.RunWorkflowReqBuilder builder = RunWorkflowReq.builder();
        builder.workflowID(wid).parameters(data);

        Flowable<WorkflowEvent> flowable = coze.workflows().run().stream(builder.build());
        flowable.subscribe(workflowEvent -> {
            System.out.println("=============== workflow event ===============");
            System.out.println(workflowEvent);
            System.out.println("=============== workflow event ===============");
        }, Throwable::printStackTrace);
    }
} 