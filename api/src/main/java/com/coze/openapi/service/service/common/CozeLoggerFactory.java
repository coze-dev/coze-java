/* (C)2024 */
package com.coze.openapi.service.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CozeLoggerFactory {
  private static volatile Logger logger = LoggerFactory.getLogger("com.coze.openapi");

  public static Logger getLogger() {
    return logger;
  }

  public static void setLogger(Logger customLogger) {
    if (customLogger != null) {
      logger = customLogger;
    }
  }
}
