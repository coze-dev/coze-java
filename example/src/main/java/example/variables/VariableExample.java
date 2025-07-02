package example.variables;

import java.util.Collections;

import com.coze.openapi.client.variables.RetrieveVariableReq;
import com.coze.openapi.client.variables.RetrieveVariableResp;
import com.coze.openapi.client.variables.UpdateVariableReq;
import com.coze.openapi.client.variables.UpdateVariableResp;
import com.coze.openapi.client.variables.model.VariableValue;
import com.coze.openapi.service.service.CozeAPI;

import example.example_utils.Utils;

public class VariableExample {

  public static void main(String[] args) {
    CozeAPI coze = Utils.getCozeAPI();
    String botID = System.getenv("PUBLISHED_BOT_ID");
    String uid = System.getenv("USER_ID");
    UpdateVariableResp resp =
        coze.variables()
            .update(
                UpdateVariableReq.builder()
                    .connectorUID(uid)
                    .botID(botID)
                    .data(
                        Collections.singletonList(
                            VariableValue.builder().keyword("key").value("value").build()))
                    .build());
    System.out.println(resp);

    RetrieveVariableResp retrieveResp =
        coze.variables()
            .retrieve(RetrieveVariableReq.builder().connectorUID(uid).botID(botID).build());
    System.out.println(retrieveResp);

    coze.shutdownExecutor();
  }
}
