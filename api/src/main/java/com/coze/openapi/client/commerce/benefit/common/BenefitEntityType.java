package com.coze.openapi.client.commerce.benefit.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class BenefitEntityType {
  public static final BenefitEntityType BENEFIT_ENTITY_TYPE_ALL_DEVICE =
      new BenefitEntityType("enterprise_all_devices");

  public static final BenefitEntityType BENEFIT_ENTITY_TYPE_ALL_IDENTIFIER =
      new BenefitEntityType("enterprise_all_identifiers");

  public static final BenefitEntityType BENEFIT_ENTITY_TYPE_SINGLE_DEVICE =
      new BenefitEntityType("single_device");

  public static final BenefitEntityType BENEFIT_ENTITY_TYPE_SINGLE_IDENTIFIER =
      new BenefitEntityType("single_identifier");
  private final String value;

  private BenefitEntityType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static BenefitEntityType fromString(String value) {
    BenefitEntityType[] types = {BENEFIT_ENTITY_TYPE_ALL_DEVICE, BENEFIT_ENTITY_TYPE_ALL_IDENTIFIER, BENEFIT_ENTITY_TYPE_SINGLE_DEVICE, BENEFIT_ENTITY_TYPE_SINGLE_IDENTIFIER};
    for (BenefitEntityType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return new BenefitEntityType(value);
  }
}
