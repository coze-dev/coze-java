package com.coze.openapi.service.service.connector;

import com.coze.openapi.api.ConnectorAPI;
import com.coze.openapi.client.connectors.InstallConnectorReq;
import com.coze.openapi.client.connectors.InstallConnectorResp;
import com.coze.openapi.service.utils.Utils;

public class ConnectorService {

  private final ConnectorAPI ConnectorAPI;

  public ConnectorService(ConnectorAPI ConnectorAPI) {
    this.ConnectorAPI = ConnectorAPI;
  }

  public InstallConnectorResp install(InstallConnectorReq req) {
    return Utils.execute(ConnectorAPI.install(req.getConnectorID(), req, req)).getData();
  }
}
