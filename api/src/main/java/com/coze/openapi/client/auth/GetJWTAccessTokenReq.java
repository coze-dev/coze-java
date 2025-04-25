package com.coze.openapi.client.auth;

import com.coze.openapi.client.auth.model.SessionContext;
import com.coze.openapi.client.auth.scope.Scope;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJWTAccessTokenReq {
  private Integer ttl;
  private Scope scope;
  private String sessionName;
  private Long accountID;
  private String enterpriseID;
  private SessionContext sessionContext;
}
