package com.coze.openapi.service.service.connector;

import com.coze.openapi.api.ConnectorBotAPI;
import com.coze.openapi.client.connectors.bots.AuditReq;
import com.coze.openapi.client.connectors.bots.AuditResp;
import com.coze.openapi.service.utils.Utils;

public class ConnectorBotService {
  private final ConnectorBotAPI ConnectorBotAPI;

  public ConnectorBotService(ConnectorBotAPI ConnectorBotAPI) {
    this.ConnectorBotAPI = ConnectorBotAPI;
  }

  public AuditResp audit(AuditReq req) {

    return Utils.execute(ConnectorBotAPI.audit(req.getConnectorID(), req.getBotID(), req, req))
        .getData();
  }
}
