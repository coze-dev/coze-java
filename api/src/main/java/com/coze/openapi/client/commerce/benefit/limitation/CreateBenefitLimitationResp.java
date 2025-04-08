package com.coze.openapi.client.commerce.benefit.limitation;

import com.coze.openapi.client.commerce.benefit.limitation.model.BenefitInfo;
import com.coze.openapi.client.common.BaseResp;
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
public class CreateBenefitLimitationResp extends BaseResp {
  @JsonProperty("benefit_info")
  private BenefitInfo benefitInfo;
}
