package com.coze.openapi.client.template.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class TemplateEntityType {
  public static final TemplateEntityType AGENT = new TemplateEntityType("agent");

  @JsonValue private final String value;

  private TemplateEntityType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static TemplateEntityType fromString(String value) {
    if ("agent".equals(value)) {
      return AGENT;
    }
    return new TemplateEntityType(value);
  }
}
