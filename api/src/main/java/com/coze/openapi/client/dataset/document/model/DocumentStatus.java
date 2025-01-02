package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class DocumentStatus {
  public static final DocumentStatus PROCESSING = new DocumentStatus(0);
  public static final DocumentStatus COMPLETED = new DocumentStatus(1);
  public static final DocumentStatus FAILED = new DocumentStatus(9);

  @JsonValue private final Integer value;

  private DocumentStatus(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static DocumentStatus fromValue(Integer value) {
    if (value == 0) {
      return PROCESSING;
    } else if (value == 1) {
      return COMPLETED;
    } else if (value == 9) {
      return FAILED;
    }
    return new DocumentStatus(value);
  }
}
