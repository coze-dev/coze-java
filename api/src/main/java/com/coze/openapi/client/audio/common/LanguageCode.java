package com.coze.openapi.client.audio.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class LanguageCode {
  public static final LanguageCode ZH = new LanguageCode("zh");
  public static final LanguageCode EN = new LanguageCode("en");
  public static final LanguageCode JA = new LanguageCode("ja");
  public static final LanguageCode ES = new LanguageCode("es");
  public static final LanguageCode ID = new LanguageCode("id");
  public static final LanguageCode PT = new LanguageCode("pt");

  private final String value;

  private LanguageCode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static LanguageCode fromString(String value) {
    LanguageCode[] codes = {ZH, EN, JA, ES, ID, PT};
    for (LanguageCode code : codes) {
      if (code.value.equals(value)) {
        return code;
      }
    }
    return new LanguageCode(value);
  }
}
