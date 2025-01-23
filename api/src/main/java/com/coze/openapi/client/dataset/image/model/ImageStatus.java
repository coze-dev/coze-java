package com.coze.openapi.client.dataset.image.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class ImageStatus {
  public static final ImageStatus IN_PROCESSING = new ImageStatus(0); // In processing
  public static final ImageStatus COMPLETED = new ImageStatus(1); // Completed
  public static final ImageStatus PROCESSING_FAILED = new ImageStatus(9); // Processing failed

  @JsonValue private final Integer value;

  private ImageStatus(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static ImageStatus fromValue(Integer value) {
    if (value == 0) {
      return IN_PROCESSING;
    } else if (value == 1) {
      return COMPLETED;
    } else if (value == 9) {
      return PROCESSING_FAILED;
    }
    return new ImageStatus(value);
  }
}
