package com.coze.openapi.service.utils;

import java.io.InputStream;
import java.util.Properties;

public class VersionUtils {
  private static final String VERSION = readVersion();

  private static String readVersion() {
    Properties prop = new Properties();
    try (InputStream input =
        VersionUtils.class.getClassLoader().getResourceAsStream("version.properties")) {
      if (input != null) {
        prop.load(input);
        return prop.getProperty("sdk.version", "unknown");
      }
    } catch (Exception e) {
      // ignore
    }
    return "unknown";
  }

  public static String getVersion() {
    return VERSION;
  }
}
