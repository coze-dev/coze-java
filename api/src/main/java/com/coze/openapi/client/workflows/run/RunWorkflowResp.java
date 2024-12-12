package com.coze.openapi.client.workflows.run;

import com.coze.openapi.client.common.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RunWorkflowResp extends BaseResponse<String> {
    @JsonProperty("execute_id")
    private String executeID;
    @JsonProperty("debug_url")
    private String debugURL;
    @JsonProperty("token")
    private Integer token;
    @JsonProperty("cost")
    private String cost;
}
