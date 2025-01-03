package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class DocumentUpdateType {
  /** Do not automatically update 不自动更新 */
  public static final DocumentUpdateType NO_AUTO_UPDATE = new DocumentUpdateType(0);
  /** Automatically update 自动更新 */
  public static final DocumentUpdateType AUTO_UPDATE = new DocumentUpdateType(1);

  @JsonValue private final Integer value;

  private DocumentUpdateType(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static DocumentUpdateType fromValue(Integer value) {
    DocumentUpdateType[] types = {NO_AUTO_UPDATE, AUTO_UPDATE};
    for (DocumentUpdateType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return new DocumentUpdateType(value);
  }
}
