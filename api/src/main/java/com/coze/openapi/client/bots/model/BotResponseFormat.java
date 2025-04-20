package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class BotResponseFormat {
  public static final BotResponseFormat TEXT = new BotResponseFormat("text");

  public static final BotResponseFormat MARKDOWN = new BotResponseFormat("markdown");

  public static final BotResponseFormat JSON = new BotResponseFormat("json");

  @JsonValue private final String value;

  private BotResponseFormat(String value) {
    this.value = value;
  }

  @JsonCreator
  public static BotResponseFormat fromValue(String value) {
    BotResponseFormat[] formats = {TEXT, MARKDOWN, JSON};
    for (BotResponseFormat type : formats) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return new BotResponseFormat(value);
  }
}
