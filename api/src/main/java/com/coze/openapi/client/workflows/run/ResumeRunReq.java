package com.coze.openapi.client.workflows.run;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
public class ResumeRunReq {
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

    protected ResumeRunReq(String workflowID, String eventID, String resumeData, Integer interruptType) {
        this.workflowID = workflowID;
        this.eventID = eventID;
        this.resumeData = resumeData;
        this.interruptType = interruptType;
    }
}
