package com.coze.openapi.client.dataset.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class DatasetStatus {
  public static final DatasetStatus ENABLED = new DatasetStatus(1);
  public static final DatasetStatus DISABLED = new DatasetStatus(3);

  @JsonValue private final Integer value;

  private DatasetStatus(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static DatasetStatus fromValue(Integer value) {
    if (value == 1) {
      return ENABLED;
    } else if (value == 3) {
      return DISABLED;
    }
    return new DatasetStatus(value);
  }
}
