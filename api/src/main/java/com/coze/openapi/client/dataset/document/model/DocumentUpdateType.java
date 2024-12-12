/* (C)2024 */
package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DocumentUpdateType {
  /** Do not automatically update 不自动更新 */
  NO_AUTO_UPDATE(0),

  /** Automatically update 自动更新 */
  AUTO_UPDATE(1);

  private final int value;

  DocumentUpdateType(int value) {
    this.value = value;
  }

  @JsonValue
  public int getValue() {
    return value;
  }

  @JsonCreator
  public static DocumentUpdateType fromValue(int value) {
    for (DocumentUpdateType updateType : DocumentUpdateType.values()) {
      if (updateType.value == value) {
        return updateType;
      }
    }
    throw new IllegalArgumentException("Unknown DocumentUpdateType value: " + value);
  }
}
