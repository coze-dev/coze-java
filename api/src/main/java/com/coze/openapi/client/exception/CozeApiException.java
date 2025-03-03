package com.coze.openapi.client.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CozeApiException extends RuntimeException {
  @JsonProperty("code")
  private int code;

  @JsonProperty("msg")
  private String msg;

  private String logID;

  public CozeApiException(int code, String msg, String logID) {
    super(msg);
    this.code = code;
    this.msg = msg;
    this.logID = logID;
  }
}
