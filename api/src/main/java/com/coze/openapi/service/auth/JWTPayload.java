package com.coze.openapi.service.auth;

import java.util.Date;

import com.coze.openapi.client.auth.model.SessionContext;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTPayload {
  @NonNull private String iss;
  @NonNull private String aud;
  @NonNull private Date iat;
  @NonNull private Date exp;
  @NonNull private String jti;
  private String sessionName;
  private SessionContext sessionContext;
}
