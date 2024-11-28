package com.coze.openapi.client.workflows.run;

import java.util.List;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workflows.run.model.WorkflowRunHistory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RetrieveRunHistoryResp extends BaseResponse<List<WorkflowRunHistory>> {
   
}
