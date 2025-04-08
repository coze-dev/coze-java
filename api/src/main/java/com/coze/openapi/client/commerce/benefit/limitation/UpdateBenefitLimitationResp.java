package com.coze.openapi.client.commerce.benefit.limitation;

import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class UpdateBenefitLimitationResp extends BaseResp {}
