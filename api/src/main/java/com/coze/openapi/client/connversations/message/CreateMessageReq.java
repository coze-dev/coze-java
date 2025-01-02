package com.coze.openapi.client.connversations.message;

import java.util.List;
import java.util.Map;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.client.connversations.message.model.MessageObjectString;
import com.coze.openapi.client.connversations.message.model.MessageRole;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
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
public class CreateMessageReq extends BaseReq {
  /*
   * The ID of the conversation.
   * */
  @NonNull
  @JsonProperty("conversation_id")
  private String conversationID;

  /*
   * The entity that sent this message.
   * */
  @NonNull private MessageRole role;

  /*
   * The content of the message, supporting pure text, multimodal (mixed input of text, images, files),
   * cards, and various types of content.
   * */
  private String content;

  /*
   * The type of message content.
   * */
  @NonNull
  @JsonProperty("content_type")
  private MessageContentType contentType;

  /*
   * Additional information when creating a message, and this additional information will also be
   * returned when retrieving messages.
   * */
  @JsonProperty("meta_data")
  private Map<String, String> metadata;

  public void setObjectContent(List<MessageObjectString> objects) {
    this.content = Utils.toJson(objects);
    this.contentType = MessageContentType.OBJECT_STRING;
  }
}
