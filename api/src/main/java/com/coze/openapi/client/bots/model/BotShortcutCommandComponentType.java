package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class BotShortcutCommandComponentType {
  /** 文本类型 */
  public static final BotShortcutCommandComponentType TEXT =
      new BotShortcutCommandComponentType("text");
  /** 选择框类型 */
  public static final BotShortcutCommandComponentType SELECT =
      new BotShortcutCommandComponentType("select");
  /** 上传文件类型 */
  public static final BotShortcutCommandComponentType FILE =
      new BotShortcutCommandComponentType("file");

  @JsonValue private final String value;

  private BotShortcutCommandComponentType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static BotShortcutCommandComponentType fromValue(String value) {
    BotShortcutCommandComponentType[] types = {TEXT, SELECT, FILE};
    for (BotShortcutCommandComponentType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return new BotShortcutCommandComponentType(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
