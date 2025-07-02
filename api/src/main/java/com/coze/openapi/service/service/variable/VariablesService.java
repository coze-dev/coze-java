package com.coze.openapi.service.service.variable;

import com.coze.openapi.api.VariablesAPI;
import com.coze.openapi.client.variables.RetrieveVariableReq;
import com.coze.openapi.client.variables.RetrieveVariableResp;
import com.coze.openapi.client.variables.UpdateVariableReq;
import com.coze.openapi.client.variables.UpdateVariableResp;
import com.coze.openapi.service.utils.Utils;

public class VariablesService {

  private final VariablesAPI VariablesAPI;

  public VariablesService(VariablesAPI VariablesAPI) {
    this.VariablesAPI = VariablesAPI;
  }

  public RetrieveVariableResp retrieve(RetrieveVariableReq req) {
    String keywords = "";
    if (req.getKeywords() != null) {
      keywords = String.join(",", req.getKeywords());
    }

    return Utils.execute(
            VariablesAPI.retrieve(
                req.getAppID(),
                req.getBotID(),
                req.getConnectorID(),
                req.getConnectorUID(),
                keywords,
                req))
        .getData();
  }

  public UpdateVariableResp update(UpdateVariableReq req) {
    return Utils.execute(VariablesAPI.update(req, req)).getData();
  }
}
