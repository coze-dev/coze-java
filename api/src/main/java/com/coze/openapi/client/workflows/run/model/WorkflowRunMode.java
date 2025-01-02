package com.coze.openapi.client.workflows.run.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Run mode of the workflow. */
public enum WorkflowRunMode {
  /** Synchronous operation. */
  SYNCHRONOUS(0),

  /** Streaming operation. */
  STREAMING(1),

  /** Asynchronous operation. */
  ASYNCHRONOUS(2);

  private final int value;

  WorkflowRunMode(int value) {
    this.value = value;
  }

  @JsonValue
  public int getValue() {
    return value;
  }

  @JsonCreator
  public static WorkflowRunMode fromValue(int value) {
    for (WorkflowRunMode mode : WorkflowRunMode.values()) {
      if (mode.value == value) {
        return mode;
      }
    }
    throw new IllegalArgumentException("Unknown WorkflowRunMode: " + value);
  }
}
