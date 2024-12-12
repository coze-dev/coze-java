/* (C)2024 */
package com.coze.openapi.client.workflows.run;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResumeRunReq extends BaseReq {
  /*
   * The ID of the workflow, which should have been published.
   * */
  @NonNull
  @JsonProperty("workflow_id")
  private String workflowID;

  /*
   * eventID
   * */
  @NonNull
  @JsonProperty("event_id")
  private String eventID;

  /*
   * resumeData
   * */
  @NonNull
  @JsonProperty("resume_data")
  private String resumeData;

  /*
   * interruptType
   * */
  @NonNull
  @JsonProperty("interrupt_type")
  private Integer interruptType;

  protected ResumeRunReq(
      String workflowID, String eventID, String resumeData, Integer interruptType) {
    this.workflowID = workflowID;
    this.eventID = eventID;
    this.resumeData = resumeData;
    this.interruptType = interruptType;
  }
}
