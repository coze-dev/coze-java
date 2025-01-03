package com.coze.openapi.client.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class GrantType {
  public static final GrantType AUTHORIZATION_CODE = new GrantType("authorization_code");
  public static final GrantType DEVICE_CODE = new GrantType("urn:ietf:params:oauth:grant-type:device_code");
  public static final GrantType JWT_CODE = new GrantType("urn:ietf:params:oauth:grant-type:jwt-bearer");
  public static final GrantType REFRESH_TOKEN = new GrantType("refresh_token");

  private final String value;

  private GrantType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static GrantType fromString(String value) {
    GrantType[] types = {AUTHORIZATION_CODE, DEVICE_CODE, JWT_CODE, REFRESH_TOKEN};
    for (GrantType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return new GrantType(value);
  }

  @Override
  public String toString() {
    return this.value;
  }
}
