package com.coze.openapi.client.connversations.message;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.Sort;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListMessageReq extends BaseReq {
  /** The ID of the conversation. */
  @NonNull
  @JsonProperty("conversation_id")
  private String conversationID;

  /** The sorting method for the message list. */
  @JsonProperty("order")
  @Builder.Default
  private String order = Sort.DESC.getValue();

  /** The ID of the Chat. */
  @JsonProperty("chat_id")
  private String chatID;

  /** Get messages before the specified position. */
  @JsonProperty("before_id")
  private String beforeID;

  /** Get messages after the specified position. */
  @JsonProperty("after_id")
  private String afterID;

  /** The amount of data returned per query. Default is 50, with a range of 1 to 50. */
  @JsonProperty("limit")
  @Builder.Default
  private Integer limit = 20;

  @JsonProperty("bot_id")
  private String botID;
}
