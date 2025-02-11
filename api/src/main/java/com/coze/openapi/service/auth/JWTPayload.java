package com.coze.openapi.service.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTPayload {
  @NonNull private String clientID;
  @NonNull private String hostName;
  @NonNull private int ttl;
  private String sessionName;
}
