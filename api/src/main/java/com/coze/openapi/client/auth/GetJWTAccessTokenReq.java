package com.coze.openapi.client.auth;

import com.coze.openapi.client.auth.model.SessionContext;
import com.coze.openapi.client.auth.scope.Scope;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetJWTAccessTokenReq {
  private Integer ttl;
  private Scope scope;
  private String sessionName;
  private Long accountID;
  private String enterpriseID;
  private SessionContext sessionContext;
}
