package com.coze.openapi.client.workflows.run.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/** Run mode of the workflow. */
@Getter
public class WorkflowRunMode {
  /** Synchronous operation. */
  public static final WorkflowRunMode SYNCHRONOUS = new WorkflowRunMode(0);
  /** Streaming operation. */
  public static final WorkflowRunMode STREAMING = new WorkflowRunMode(1);
  /** Asynchronous operation. */
  public static final WorkflowRunMode ASYNCHRONOUS = new WorkflowRunMode(2);

  @JsonValue private final Integer value;

  private WorkflowRunMode(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static WorkflowRunMode fromValue(Integer value) {
    WorkflowRunMode[] modes = {SYNCHRONOUS, STREAMING, ASYNCHRONOUS};
    for (WorkflowRunMode mode : modes) {
      if (mode.value.equals(value)) {
        return mode;
      }
    }
    return new WorkflowRunMode(value);
  }
}
