/* (C)2024 */
package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DocumentStatus {
  /** Processing 处理中 */
  PROCESSING(0),

  /** Completed 处理完毕 */
  COMPLETED(1),

  /** Processing failed, it is recommended to re-upload 处理失败，建议重新上传 */
  FAILED(9);

  private final int value;

  DocumentStatus(int value) {
    this.value = value;
  }

  @JsonValue
  public int getValue() {
    return value;
  }

  @JsonCreator
  public static DocumentStatus fromValue(int value) {
    for (DocumentStatus status : DocumentStatus.values()) {
      if (status.value == value) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown DocumentStatus value: " + value);
  }
}
