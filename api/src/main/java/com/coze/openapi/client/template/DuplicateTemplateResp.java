package com.coze.openapi.client.template;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.template.model.TemplateEntityType;
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
public class DuplicateTemplateResp extends BaseResp {
  @JsonProperty("entity_id")
  private String entityID;

  @JsonProperty("entity_type")
  private TemplateEntityType entityType;
}
