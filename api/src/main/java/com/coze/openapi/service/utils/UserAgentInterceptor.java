package com.coze.openapi.service.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request =
        chain
            .request()
            .newBuilder()
            .addHeader("User-Agent", getUserAgent())
            .addHeader("X-Coze-Client-User-Agent", getCozeClientUserAgent())
            .build();
    return chain.proceed(request);
  }

  public static final String VERSION = "0.3.0";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  /** 获取操作系统版本 */
  private static String getOsVersion() {
    String osName = System.getProperty("os.name").toLowerCase();
    String osVersion;

    if (osName.contains("mac")) {
      osVersion = System.getProperty("os.version");
    } else if (osName.contains("windows")) {
      osVersion = System.getProperty("os.version");
    } else if (osName.contains("linux")) {
      osVersion = System.getProperty("os.version");
    } else {
      osVersion = System.getProperty("os.version");
    }

    return osVersion;
  }

  /** 获取用户代理字符串 */
  private static String getUserAgent() {
    String javaVersion = System.getProperty("java.version");
    String osName = System.getProperty("os.name").toLowerCase();
    String osVersion = getOsVersion();

    return String.format("cozejava/%s java/%s %s/%s", VERSION, javaVersion, osName, osVersion)
        .toLowerCase();
  }

  /** 获取Coze客户端用户代理JSON */
  private static String getCozeClientUserAgent() {
    try {
      Map<String, String> ua = new HashMap<>();
      ua.put("version", VERSION);
      ua.put("lang", "java");
      ua.put("lang_version", System.getProperty("java.version"));
      ua.put("os_name", System.getProperty("os.name").toLowerCase());
      ua.put("os_version", getOsVersion());
      return objectMapper.writeValueAsString(ua);
    } catch (Exception e) {
    }
    return "";
  }
}
