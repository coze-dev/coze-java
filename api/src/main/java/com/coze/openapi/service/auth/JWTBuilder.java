package com.coze.openapi.service.auth;

public interface JWTBuilder {
  String generateJWT(int ttl, String sessionName);
}
