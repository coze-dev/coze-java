/* (C)2024 */
package com.coze.openapi.client.chat.model;

import java.util.Map;

import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
  /** The ID of the chat. */
  @JsonProperty("id")
  private String id;

  public String getID() {
    return id;
  }

  /** The ID of the conversation. */
  @JsonProperty("conversation_id")
  private String conversationID;

  /** The ID of the bot. */
  @JsonProperty("bot_id")
  private String botID;

  /** Indicates the create time of the chat. The value format is Unix timestamp in seconds. */
  @JsonProperty("created_at")
  private Integer createdAt;

  /** Indicates the end time of the chat. The value format is Unix timestamp in seconds. */
  @JsonProperty("completed_at")
  private Integer completedAt;

  /** Indicates the failure time of the chat. The value format is Unix timestamp in seconds. */
  @JsonProperty("failed_at")
  private Integer failedAt;

  /**
   * Additional information when creating a message, and this additional information will also be
   * returned when retrieving messages. Custom key-value pairs should be specified in Map object
   * format, with a length of 16 key-value pairs. The length of the key should be between 1 and 64
   * characters, and the length of the value should be between 1 and 512 characters.
   */
  @JsonProperty("meta_data")
  private Map<String, String> metaData;

  /**
   * When the chat encounters an exception, this field returns detailed error information,
   * including: Code: The error code. An integer type. 0 indicates success, other values indicate
   * failure. Msg: The error message. A string type.
   */
  @JsonProperty("last_error")
  private ChatError lastError;

  /**
   * The running status of the session. The values are: created: The session has been created.
   * in_progress: The Bot is processing. completed: The Bot has finished processing, and the session
   * has ended. failed: The session has failed. requires_action: The session is interrupted and
   * requires further processing.
   */
  @JsonProperty("status")
  private ChatStatus status;

  /** Details of the information needed for execution. */
  @JsonProperty("required_action")
  private ChatRequiredAction requiredAction;

  /** Detailed information about Token consumption. */
  @JsonProperty("usage")
  private ChatUsage usage;

  public static Chat fromJson(String json) {
    return Utils.fromJson(json, Chat.class);
  }
}
