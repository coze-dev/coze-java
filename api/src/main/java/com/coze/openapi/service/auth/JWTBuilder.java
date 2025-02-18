package com.coze.openapi.service.auth;

import java.security.PrivateKey;
import java.util.Map;

public interface JWTBuilder {
  String generateJWT(PrivateKey privateKey, Map<String, Object> header, JWTPayload payload);
}
