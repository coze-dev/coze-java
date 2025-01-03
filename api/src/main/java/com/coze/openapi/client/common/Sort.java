package com.coze.openapi.client.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class Sort {
  public static final Sort DESC = new Sort("desc");
  /** Indicates that the content of the message is sent by the user. */
  public static final Sort ASC = new Sort("asc");

  private final String value;

  private Sort(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static Sort fromString(String value) {
    Sort[] sorts = {DESC, ASC};
    for (Sort sort : sorts) {
      if (sort.value.equals(value)) {
        return sort;
      }
    }
    return new Sort(value);
  }
}
