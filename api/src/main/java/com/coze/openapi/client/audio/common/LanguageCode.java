/* (C)2024 */
package com.coze.openapi.client.audio.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Language codes. */
public enum LanguageCode {
  ZH("zh"),
  EN("en"),
  JA("ja"),
  ES("es"),
  ID("id"),
  PT("pt");

  private final String value;

  LanguageCode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static LanguageCode fromString(String value) {
    for (LanguageCode code : LanguageCode.values()) {
      if (code.value.equals(value)) {
        return code;
      }
    }
    throw new IllegalArgumentException("Unknown LanguageCode: " + value);
  }
}
