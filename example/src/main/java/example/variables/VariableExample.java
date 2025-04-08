package example.variables;

import java.util.Collections;

import com.coze.openapi.client.variables.RetrieveVariablesReq;
import com.coze.openapi.client.variables.RetrieveVariablesResp;
import com.coze.openapi.client.variables.UpdateVariablesReq;
import com.coze.openapi.client.variables.UpdateVariablesResp;
import com.coze.openapi.client.variables.model.VariableEntity;
import com.coze.openapi.service.service.CozeAPI;

import example.example_utils.Utils;

public class VariableExample {

  public static void main(String[] args) {
    CozeAPI coze = Utils.getCozeAPI();
    String botID = System.getenv("PUBLISHED_BOT_ID");
    String uid = System.getenv("USER_ID");
    UpdateVariablesResp resp =
        coze.variables()
            .update(
                UpdateVariablesReq.builder()
                    .connectorUID(uid)
                    .botID(botID)
                    .data(
                        Collections.singletonList(
                            VariableEntity.builder().keyword("key").value("value").build()))
                    .build());
    System.out.println(resp);

    RetrieveVariablesResp retrieveResp =
        coze.variables()
            .retrieve(RetrieveVariablesReq.builder().connectorUID(uid).botID(botID).build());
    System.out.println(retrieveResp);

    coze.shutdownExecutor();
  }
}
