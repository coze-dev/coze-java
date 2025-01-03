package com.coze.openapi.client.dataset.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class DatasetStatus {
  public static final DatasetStatus ENABLE = new DatasetStatus(1);
  public static final DatasetStatus DISABLE = new DatasetStatus(3);

  @JsonValue 
  private final Integer value;

  private DatasetStatus(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static DatasetStatus fromValue(Integer value) {
    DatasetStatus[] statuses = {ENABLE, DISABLE};
    for (DatasetStatus status : statuses) {
      if (status.value.equals(value)) {
        return status;
      }
    }
    return new DatasetStatus(value);
  }
}
