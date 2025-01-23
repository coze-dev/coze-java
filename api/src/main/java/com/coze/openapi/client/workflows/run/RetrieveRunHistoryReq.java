package com.coze.openapi.client.workflows.run;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveRunHistoryReq extends BaseReq {

  /*
   * The ID of the workflow.
   * */
  @NonNull
  @JsonProperty("execute_id")
  private String executeID;

  /*
   * The ID of the workflow async execute.
   * */
  @NonNull
  @JsonProperty("workflow_id")
  private String workflowID;

  public static RetrieveRunHistoryReq of(String workflowID, String executeID) {
    return RetrieveRunHistoryReq.builder().executeID(executeID).workflowID(workflowID).build();
  }
}
