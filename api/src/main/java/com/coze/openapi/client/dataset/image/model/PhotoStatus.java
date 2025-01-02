package com.coze.openapi.client.dataset.image.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class PhotoStatus {
  public static final PhotoStatus IN_PROCESSING = new PhotoStatus(0); // In processing
  public static final PhotoStatus COMPLETED = new PhotoStatus(1); // Completed
  public static final PhotoStatus PROCESSING_FAILED = new PhotoStatus(9); // Processing failed

  @JsonValue private final Integer value;

  private PhotoStatus(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static PhotoStatus fromValue(Integer value) {
    if (value == 0) {
      return IN_PROCESSING;
    } else if (value == 1) {
      return COMPLETED;
    } else if (value == 9) {
      return PROCESSING_FAILED;
    }
    return new PhotoStatus(value);
  }
}
