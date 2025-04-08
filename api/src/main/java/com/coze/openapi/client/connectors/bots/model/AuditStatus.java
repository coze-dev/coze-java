package com.coze.openapi.client.connectors.bots.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class AuditStatus {
  public static final AuditStatus IN_PROGRESS = new AuditStatus(1);
  public static final AuditStatus PASS = new AuditStatus(2);
  public static final AuditStatus REJECT = new AuditStatus(3);

  private final Integer value;

  private AuditStatus(Integer value) {
    this.value = value;
  }

  @JsonValue
  public Integer getValue() {
    return value;
  }

  @JsonCreator
  public static AuditStatus fromString(Integer value) {
    AuditStatus[] statuses = {IN_PROGRESS, PASS, REJECT};
    for (AuditStatus status : statuses) {
      if (status.value.equals(value)) {
        return status;
      }
    }
    return new AuditStatus(value);
  }
}
