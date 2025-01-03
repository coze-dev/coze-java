package com.coze.openapi.service.auth;

public class TokenAuth extends Auth {
  private final String accessToken;

  public TokenAuth(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public String token() {
    return accessToken;
  }
}
