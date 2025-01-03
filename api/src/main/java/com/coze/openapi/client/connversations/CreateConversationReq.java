package com.coze.openapi.client.connversations;

import java.util.List;
import java.util.Map;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateConversationReq extends BaseReq {

  /*
   * Messages in the conversation. For more information, see EnterMessage object.
   * */
  @JsonProperty("messages")
  private List<Message> messages;

  /*
  *  Additional information when creating a message, and this additional information will also be
      returned when retrieving messages.
  * */
  @JsonProperty("meta_data")
  private Map<String, String> metaData;

  /*
   * Bind and isolate conversation on different bots.
   * */
  @JsonProperty("bot_id")
  private String botID;
}
