package com.coze.openapi.service.auth;

import java.lang.reflect.Field;

public class TestUtils {
  public static void setField(Object target, String fieldName, Object value) {
    try {
      Field field = target.getClass().getSuperclass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(target, value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
