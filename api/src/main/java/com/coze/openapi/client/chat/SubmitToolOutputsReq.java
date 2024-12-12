/* (C)2024 */
package com.coze.openapi.client.chat;

import java.util.List;

import com.coze.openapi.client.chat.model.ToolOutput;
import com.coze.openapi.client.common.BaseReq;
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
public class SubmitToolOutputsReq extends BaseReq {

  /*
   *  The Conversation ID can be viewed in the 'conversation_id' field of the Response when
   *  initiating a conversation through the Chat API.
   * */
  @NonNull
  @JsonProperty("conversation_id")
  private String conversationID;

  /*
   * The Chat ID can be viewed in the 'id' field of the Response when initiating a chat through the
   *  Chat API. If it is a streaming response, check the 'id' field in the chat event of the Response.
   * */
  @NonNull
  @JsonProperty("chat_id")
  private String chatID;

  /*
   * The execution result of the tool. For detailed instructions, refer to the ToolOutput Object
   * */
  @NonNull
  @JsonProperty("tool_outputs")
  private List<ToolOutput> toolOutputs;

  @JsonProperty("stream")
  private Boolean stream;

  public void enableStream() {
    this.stream = true;
  }

  public void disableStream() {
    this.stream = false;
  }

  public void clearBeforeReq() {
    this.chatID = null;
    this.conversationID = null;
  }
}
