package com.coze.openapi.client.commerce.benefit.common;

import com.fasterxml.jackson.annotation.JsonValue;

public class BenefitType {
  public static final BenefitType BENEFIT_TYPE_RESOURCE_POINT = new BenefitType("resource_point");

  private final String value;

  private BenefitType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
