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
public class WorkflowEventError {
    /**
     * Status code. 0 represents a successful API call. Other values indicate that the call has failed. You can
     * determine the detailed reason for the error through the error_message field.
     */
    @JsonProperty("error_code")
    private int errorCode;

    /**
     * Status message. You can get detailed error information when the API call fails.
     */
    @JsonProperty("error_message")
    private String errorMessage;

    public static WorkflowEventError fromJson(String data) {
        return Utils.fromJson(data, WorkflowEventError.class);
    }
} 