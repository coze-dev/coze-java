package com.coze.openapi.utils;

import java.util.HashMap;

import okhttp3.Headers;

public class Utils {
  public static final String LOG_HEADER = "x-tt-logid";
  public static final String TEST_LOG_ID = "test-log-id";
  private static final Headers commonHeader =
      Headers.of(
          new HashMap<String, String>() {
            {
              put(LOG_HEADER, TEST_LOG_ID);
            }
          });

  public static Headers getCommonHeader() {
    return commonHeader;
  }
}
