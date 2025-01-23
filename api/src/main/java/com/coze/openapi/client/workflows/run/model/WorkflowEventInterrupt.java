package com.coze.openapi.client.workflows.run.model;

import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowEventInterrupt {
  /** The content of interruption event. */
  @JsonProperty("interrupt_data")
  private WorkflowEventInterruptData interruptData;

  /** The name of the node that outputs the message, such as "Question". */
  @JsonProperty("node_title")
  private String nodeTitle;

  public static WorkflowEventInterrupt fromJson(String data) {
    return Utils.fromJson(data, WorkflowEventInterrupt.class);
  }
}
