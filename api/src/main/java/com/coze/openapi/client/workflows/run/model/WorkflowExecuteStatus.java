/* (C)2024 */
package com.coze.openapi.client.workflows.run.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Execution status of the workflow. */
@Getter
@AllArgsConstructor
public class WorkflowExecuteStatus {
  /** Execution succeeded. */
  public static final WorkflowExecuteStatus SUCCESS = new WorkflowExecuteStatus("Success");

  /** Execution in progress. */
  public static final WorkflowExecuteStatus RUNNING = new WorkflowExecuteStatus("Running");

  /** Execution failed. */
  public static final WorkflowExecuteStatus FAIL = new WorkflowExecuteStatus("Fail");

  private final String value;

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static WorkflowExecuteStatus fromString(String value) {
    if (value == null) {
      return new WorkflowExecuteStatus("");
    }

    if (value.equals(SUCCESS.getValue())) return SUCCESS;
    if (value.equals(RUNNING.getValue())) return RUNNING;
    if (value.equals(FAIL.getValue())) return FAIL;

    return new WorkflowExecuteStatus(value);
  }
}
