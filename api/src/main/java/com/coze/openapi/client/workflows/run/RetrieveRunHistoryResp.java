package com.coze.openapi.client.workflows.run;

import java.util.List;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.workflows.run.model.WorkflowRunHistory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RetrieveRunHistoryResp extends BaseResp {
  private List<WorkflowRunHistory> histories;
}
