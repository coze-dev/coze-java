package com.coze.openapi.service.auth;

import java.security.PrivateKey;
import java.util.Map;

public interface JWTBuilder {
  String generateJWT(
      Map<String, Object> header, PrivateKey privateKey, int ttl, String sessionName);
}
