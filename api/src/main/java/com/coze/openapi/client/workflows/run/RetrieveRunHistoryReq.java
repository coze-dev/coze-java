package com.coze.openapi.client.workflows.run;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveRunHistoryReq {
    @NonNull
    @JsonProperty("execute_id")
    private String executeID;
    @NonNull
    @JsonProperty("workflow_id")
    private String workflowID;

    public static RetrieveRunHistoryReq of(String workflowID, String executeID) {
        return RetrieveRunHistoryReq.builder().executeID(executeID).workflowID(workflowID).build();
    }
}
