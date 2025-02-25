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
public class WorkflowEventDebugURL {
  @JsonProperty("debug_url")
  private String debugURL;

  public static WorkflowEventDebugURL fromJson(String data) {
    return Utils.fromJson(data, WorkflowEventDebugURL.class);
  }
}
