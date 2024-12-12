/* (C)2024 */
package com.coze.openapi.client.exception;

import lombok.Getter;
import lombok.ToString;

// todo 放到中间件中，并且解析出 logid 放到里面
@Getter
@ToString
public class CozeAuthException extends RuntimeException {

  private final int statusCode;
  private final AuthErrorCode code;
  private final String errorMessage;
  private final String param;
  private final String logID;

  public CozeAuthException(CozeError error, Exception parent, int statusCode, String logID) {
    super(error.errorMessage, parent);
    this.statusCode = statusCode;
    this.errorMessage = error.errorMessage;
    this.code = AuthErrorCode.fromString(error.errorCode);
    this.param = error.error;
    this.logID = logID;
  }

  public CozeAuthException(CozeError error, int statusCode, String logID) {
    super(error.errorMessage);
    this.statusCode = statusCode;
    this.errorMessage = error.errorMessage;
    this.code = AuthErrorCode.fromString(error.errorCode);
    this.param = error.error;
    this.logID = logID;
  }
}
