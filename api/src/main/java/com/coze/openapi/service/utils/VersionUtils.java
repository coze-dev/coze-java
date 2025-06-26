package com.coze.openapi.service.utils;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VersionUtils {

  private static final Logger logger = LoggerFactory.getLogger(VersionUtils.class);

  private static final String VERSION = readVersion();

  private static String readVersion() {
    Properties prop = new Properties();
    try (InputStream input =
        VersionUtils.class.getClassLoader().getResourceAsStream("version.properties")) {
      if (input != null) {
        prop.load(input);
        return prop.getProperty("sdk.version", "");
      }
    } catch (Exception e) {
      logger.error("Failed to read version.properties->ex:{}", e.getMessage());
    }
    return "";
  }

  public static String getVersion() {
    return VERSION;
  }
}
