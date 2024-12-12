/* (C)2024 */
package com.coze.openapi.client.connversations.message;

import java.util.List;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListMessageResp extends BaseResponse<List<Message>> {
  @JsonProperty("has_more")
  private boolean hasMore;

  @JsonProperty("first_id")
  private String firstID;

  @JsonProperty("last_id")
  private String lastID;
}
