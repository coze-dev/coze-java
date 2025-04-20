package com.coze.openapi.service.auth;

public abstract class Auth {

  protected String accessToken;
  protected String refreshToken;
  protected long refreshAt;
  protected long expiresIn;
  protected OAuthClient client;

  /**
   * 获取token类型
   *
   * @return token类型，默认返回 Bearer
   */
  public String tokenType() {
    return "Bearer";
  }

  /**
   * 获取token
   *
   * @return token
   */
  public abstract String token();
}
