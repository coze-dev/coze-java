/* (C)2024 */
package com.coze.openapi.service.service.workflow;

import java.util.List;

import com.coze.openapi.api.WorkflowRunHistoryAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.workflows.run.RetrieveRunHistoryReq;
import com.coze.openapi.client.workflows.run.RetrieveRunHistoryResp;
import com.coze.openapi.client.workflows.run.model.WorkflowRunHistory;
import com.coze.openapi.service.utils.Utils;

public class WorkflowRunHistoryService {
  private final WorkflowRunHistoryAPI workflowRunHistoryAPI;

  public WorkflowRunHistoryService(WorkflowRunHistoryAPI workflowRunAPI) {
    this.workflowRunHistoryAPI = workflowRunAPI;
  }

  /*
   * After the workflow runs async, retrieve the execution results.
   * docs cn: https://www.coze.cn/docs/developer_guides/workflow_history
   * docs en: https://www.coze.com/docs/developer_guides/workflow_history
   * */
  public RetrieveRunHistoryResp retrieve(RetrieveRunHistoryReq req) {
    BaseResponse<List<WorkflowRunHistory>> resp =
        Utils.execute(workflowRunHistoryAPI.retrieve(req.getWorkflowID(), req.getExecuteID(), req));
    return RetrieveRunHistoryResp.builder()
        .histories(resp.getData())
        .logID(resp.getLogID())
        .build();
  }
}
