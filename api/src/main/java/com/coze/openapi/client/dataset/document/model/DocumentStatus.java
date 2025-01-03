package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class DocumentStatus {
  /** Processing 处理中 */
  public static final DocumentStatus PROCESSING = new DocumentStatus(0);
  /** Completed 处理完毕 */
  public static final DocumentStatus COMPLETED = new DocumentStatus(1);
  /** Processing failed, it is recommended to re-upload 处理失败，建议重新上传 */
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
