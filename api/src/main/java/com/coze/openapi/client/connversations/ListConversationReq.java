package com.coze.openapi.client.connversations;

import com.coze.openapi.client.common.BaseReq;
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
public class ListConversationReq extends BaseReq {
  /** The ID of the bot. */
  @NonNull
  @JsonProperty("bot_id")
  private String botID;

  /** The page number. */
  @Builder.Default
  @JsonProperty("page_num")
  private Integer pageNum = 1;

  /** The page size. */
  @Builder.Default
  @JsonProperty("page_size")
  private Integer pageSize = 20;

  public static ListConversationReq of(String botID) {
    return ListConversationReq.builder().botID(botID).build();
  }
}
