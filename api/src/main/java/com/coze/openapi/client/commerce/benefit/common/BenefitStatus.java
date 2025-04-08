package com.coze.openapi.client.commerce.benefit.common;

import com.fasterxml.jackson.annotation.JsonValue;

public class BenefitStatus {
  public static final BenefitStatus BENEFIT_STATUS_VALID = new BenefitStatus("valid");

  /** The server is processing the conversation. */
  public static final BenefitStatus BENEFIT_STATUS_FROZEN = new BenefitStatus("frozen");

  private final String value;

  private BenefitStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
