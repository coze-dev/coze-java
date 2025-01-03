package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DocumentSourceType {
  /** Upload local files. 上传本地文件。 */
  LOCAL_FILE(0),

  /** Upload online web pages. 上传在线网页。 */
  ONLINE_WEB(1);

  private final int value;

  DocumentSourceType(int value) {
    this.value = value;
  }

  @JsonValue
  public int getValue() {
    return value;
  }

  @JsonCreator
  public static DocumentSourceType fromValue(int value) {
    for (DocumentSourceType type : DocumentSourceType.values()) {
      if (type.value == value) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown DocumentSourceType value: " + value);
  }
}
