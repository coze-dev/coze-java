package com.coze.openapi.client.commerce.benefit.limitation;

import java.util.List;

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
public class ListBenefitLimitationResp extends BaseResp {
  @JsonProperty("has_more")
  private boolean hasMore;

  @JsonProperty("page_token")
  private String pageToken;

  @JsonProperty("benefit_infos")
  private List<BenefitInfo> benefitInfos;
}
