package com.coze.openapi.client.workflows.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDetail {

  /** 工作流关联的应用 ID。若工作流未关联任何应用，则该字段值为 0 */
  @JsonProperty("app_id")
  private String app_id;
  /** 工作流创建者的信息，包含创建者的用户 ID 和用户名 */
  @JsonProperty("creator")
  private WorkflowCreator creator;
  /** 工作流图标的 URL 地址。 */
  @JsonProperty("icon_url")
  private String iconUrl;
  /** 工作流的创建时间，以 Unix 时间戳表示，单位为秒 */
  @JsonProperty("created_at")
  private String createdAt;
  /** 工作流的最后更新时间，以 Unix 时间戳表示，单位为秒 */
  @JsonProperty("updated_at")
  private String updatedAt;
  /** 工作流的描述。 */
  @JsonProperty("description")
  private String description;
  /** 工作流 ID。 */
  @JsonProperty("workflow_id")
  private String workflowId;
  /** 工作流名称。 */
  @JsonProperty("workflow_name")
  private String workflowName;
}
