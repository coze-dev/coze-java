package com.coze.openapi.client.commerce.benefit.limitation;

import com.coze.openapi.client.commerce.benefit.common.BenefitStatus;
import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class UpdateBenefitLimitationReq extends BaseReq {
  @NonNull @JsonIgnore private String benefitID;

  @JsonProperty("started_at")
  private Long startedAt;

  @JsonProperty("ended_at")
  private Long endedAt;

  @JsonProperty("limit")
  private Integer limit;

  @JsonProperty("status")
  private BenefitStatus status;
}
