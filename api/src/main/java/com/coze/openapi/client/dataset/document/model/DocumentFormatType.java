package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class DocumentFormatType {
  public static final DocumentFormatType TEXT = new DocumentFormatType(0);
  public static final DocumentFormatType TABLE = new DocumentFormatType(1);
  public static final DocumentFormatType IMAGE = new DocumentFormatType(2);

  @JsonValue private final Integer value;

  private DocumentFormatType(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static DocumentFormatType fromValue(Integer value) {
    if (value == 0) {
      return TEXT;
    } else if (value == 1) {
      return TABLE;
    } else if (value == 2) {
      return IMAGE;
    }
    return new DocumentFormatType(value);
  }
}
