package com.coze.openapi.client.commerce.benefit.limitation;

import com.coze.openapi.client.commerce.benefit.common.BenefitEntityType;
import com.coze.openapi.client.commerce.benefit.common.BenefitStatus;
import com.coze.openapi.client.commerce.benefit.common.BenefitType;
import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class ListBenefitLimitationReq extends BaseReq {
  @JsonProperty("entity_id")
  private BenefitEntityType entityType;

  @JsonProperty("entity_id")
  private String entityID;

  @JsonProperty("benefit_type")
  private BenefitType benefitType;

  @JsonProperty("status")
  private BenefitStatus status;

  @JsonProperty("page_token")
  private String pageToken;

  @JsonProperty("page_size")
  private Integer pageSize;
}
