package com.coze.openapi.client.workflows;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowListReq extends BaseReq {

  /** 工作空间 ID，用于指定要查询的工作空间。 */
  @NonNull
  @JsonProperty("workspace_id")
  private String workspaceId;

  /** 查询结果分页展示时，此参数用于设置查看的页码。最小值为 1。 */
  @NonNull
  @JsonProperty("page_num")
  private Integer pageNum;
  /** 查询结果分页展示时，此参数用于设置每页返回的数据量。取值范围为 1 ~ 30，默认为 10。 */
  @JsonProperty("page_size")
  private Integer pageSize;
  /** 工作流类型，默认为空，即查询所有工作流类型。枚举值： workflow：工作流。 chatflow：对话流。 */
  @JsonProperty("workflow_mode")
  private String workflowMode;
  /** 扣子应用 ID，用于查询指定应用关联的工作流。默认为空，即不指定应用。 */
  @JsonProperty("app_id")
  private String appId;
  /**
   * 工作流的发布状态，用于筛选不同发布状态的工作流。枚举值： all ：所有状态。 published_online ：（默认值）已发布的正式版。 unpublished_draft
   * ：当前为草稿状态。
   */
  @JsonProperty("publish_status")
  private String publishStatus;
}
