package com.coze.openapi.client.connectors.bots;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.connectors.bots.model.AuditStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AuditReq extends BaseReq {
  @JsonIgnore private String BotID;
  @JsonIgnore private String ConnectorID;

  @JsonProperty("audit_status")
  private AuditStatus auditStatus;

  @JsonProperty("reason")
  private String reason;
}
