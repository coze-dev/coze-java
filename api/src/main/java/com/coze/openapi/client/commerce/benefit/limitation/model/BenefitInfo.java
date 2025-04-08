package com.coze.openapi.client.commerce.benefit.limitation.model;

import com.coze.openapi.client.commerce.benefit.common.ActiveMode;
import com.coze.openapi.client.commerce.benefit.common.BenefitStatus;
import com.coze.openapi.client.commerce.benefit.common.BenefitType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitInfo {
  @JsonProperty("benefit_id")
  private String benefitID;

  @JsonProperty("benefit_type")
  private BenefitType benefitType;

  @JsonProperty("active_mode")
  private ActiveMode activeMode;

  @JsonProperty("started_at")
  private Long startedAt;

  @JsonProperty("ended_at")
  private Long endedAt;

  @JsonProperty("limit")
  private Integer limit;

  @JsonProperty("status")
  private BenefitStatus status;
}
