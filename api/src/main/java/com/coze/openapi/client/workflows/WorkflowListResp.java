package com.coze.openapi.client.workflows;

import java.util.List;

import com.coze.openapi.client.workflows.model.WorkflowDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkflowListResp {
  /** 工作流的基础信息。 */
  @JsonProperty("items")
  private List<WorkflowDetail> items;
  /** 标识当前返回的工作流列表是否还有更多数据未返回。 true ：还有更多未返回的回调应用。 false：已返回所有数据。 */
  @JsonProperty("has_more")
  private Boolean hasMore;
}
