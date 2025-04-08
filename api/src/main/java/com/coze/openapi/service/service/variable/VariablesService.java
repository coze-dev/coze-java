package com.coze.openapi.service.service.variable;

import com.coze.openapi.api.VariablesAPI;
import com.coze.openapi.client.variables.RetrieveVariablesReq;
import com.coze.openapi.client.variables.RetrieveVariablesResp;
import com.coze.openapi.client.variables.UpdateVariablesReq;
import com.coze.openapi.client.variables.UpdateVariablesResp;
import com.coze.openapi.service.utils.Utils;

public class VariablesService {

  private final VariablesAPI VariablesAPI;

  public VariablesService(VariablesAPI VariablesAPI) {
    this.VariablesAPI = VariablesAPI;
  }

  public RetrieveVariablesResp retrieve(RetrieveVariablesReq req) {
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

  public UpdateVariablesResp update(UpdateVariablesReq req) {
    return Utils.execute(VariablesAPI.update(req, req)).getData();
  }
}
