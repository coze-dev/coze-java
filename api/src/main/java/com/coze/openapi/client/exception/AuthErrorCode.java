/* (C)2024 */
package com.coze.openapi.client.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/** 认证错误码类 */
@Getter
public class AuthErrorCode {
  /*
   * The user has not completed authorization yet, please try again later
   */
  public static final AuthErrorCode AUTHORIZATION_PENDING =
      new AuthErrorCode("authorization_pending");

  /*
   * The request is too frequent, please try again later
   */
  public static final AuthErrorCode SLOW_DOWN = new AuthErrorCode("slow_down");

  /*
   * The user has denied the authorization
   */
  public static final AuthErrorCode ACCESS_DENIED = new AuthErrorCode("access_denied");

  /*
   * The token is expired
   */
  public static final AuthErrorCode EXPIRED_TOKEN = new AuthErrorCode("expired_token");

  /** 错误码的字符串值 */
  private final String value;

  /**
   * 私有构造函数，防止外部创建新实例
   *
   * @param value 错误码字符串值
   */
  private AuthErrorCode(String value) {
    this.value = value;
  }

  /**
   * 获取错误码的值，用于 JSON 序列化
   *
   * @return 错误码字符串值
   */
  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.value;
  }

  /**
   * 从字符串创建错误码实例
   *
   * @param value 错误码字符串值
   * @return 对应的错误码实例
   * @throws IllegalArgumentException 如果找不到对应的错误码
   */
  @JsonCreator
  public static AuthErrorCode fromString(String value) {
    if (value == null) {
      return null;
    }

    if (value.equals(AUTHORIZATION_PENDING.value)) return AUTHORIZATION_PENDING;
    if (value.equals(SLOW_DOWN.value)) return SLOW_DOWN;
    if (value.equals(ACCESS_DENIED.value)) return ACCESS_DENIED;
    if (value.equals(EXPIRED_TOKEN.value)) return EXPIRED_TOKEN;

    return new AuthErrorCode(value);
  }
}
