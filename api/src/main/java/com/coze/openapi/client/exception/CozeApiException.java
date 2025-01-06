package com.coze.openapi.client.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CozeApiException extends RuntimeException {
  private final int code;
  private final String msg;
  private final String logID;

  public CozeApiException(int code, String msg, String logID) {
    super(msg);
    this.code = code;
    this.msg = msg;
    this.logID = logID;
  }
}
