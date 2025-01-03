package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class ChatStatus {
  /** The session has been created. */
  public static final ChatStatus CREATED = new ChatStatus("created");
  /** The Bot is processing. */
  public static final ChatStatus IN_PROGRESS = new ChatStatus("in_progress");
  /** The Bot has finished processing, and the session has ended. */
  public static final ChatStatus COMPLETED = new ChatStatus("completed");
  /** The session has failed. */
  public static final ChatStatus FAILED = new ChatStatus("failed");
  /** The session is interrupted and requires further processing. */
  public static final ChatStatus REQUIRES_ACTION = new ChatStatus("requires_action");
  /** The session is user cancelled chat. */
  public static final ChatStatus CANCELLED = new ChatStatus("canceled");

  private final String value;

  private ChatStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static ChatStatus fromString(String value) {
    ChatStatus[] statuses = {CREATED, IN_PROGRESS, COMPLETED, FAILED, REQUIRES_ACTION, CANCELLED};
    for (ChatStatus status : statuses) {
      if (status.value.equals(value)) {
        return status;
      }
    }
    return new ChatStatus(value);
  }
}
