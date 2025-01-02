package com.coze.openapi.client.template;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class DuplicateTemplateReq extends BaseReq {
  @JsonIgnore private String templateID;

  @JsonProperty("workspace_id")
  private String workspaceID;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("name")
  private String name;
}
