package com.coze.openapi.client.workflows.run;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunWorkflowReq {
    @JsonProperty("workflow_id")
    private String workflowID;
    private Map<String, Object> parameters;
    @JsonProperty("bot_id")
    private String botID;
    @JsonProperty("ext")
    private Map<String, String> ext;
    @JsonProperty("is_async")
    private Boolean isAsync;
}
