package com.coze.openapi.client.chat;

import java.util.List;
import java.util.Map;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.connversations.message.model.Message;
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
public class CreateChatReq extends BaseReq {
  /*
   * Indicate which conversation the chat is taking place in.
   * */
  @JsonProperty("conversation_id")
  private String conversationID;

  /*
   * The ID of the bot that the API interacts with.
   * */
  @NonNull
  @JsonProperty("bot_id")
  private String botID;

  /*
   * The user who calls the API to chat with the bot.
   * */
  @NonNull
  @JsonProperty("user_id")
  private String userID;

  /*
   *  Additional information for the conversation. You can pass the user's query for this
   *  conversation through this field. The array length is limited to 100, meaning up to 100 messages can be input.
   * */
  @JsonProperty("additional_messages")
  private List<Message> messages;

  /*
   * developer can ignore this param
   * */
  @JsonProperty("stream")
  private Boolean stream;

  /*
   * The customized variable in a key-value pair.
   * */
  @JsonProperty("custom_variables")
  private Map<String, String> customVariables;

  /*
   * Whether to automatically save the history of conversation records.
   * */
  @JsonProperty("auto_save_history")
  private Boolean autoSaveHistory;

  /*
   * Additional information, typically used to encapsulate some business-related fields.
   * */
  @JsonProperty("meta_data")
  private Map<String, String> metaData;

  /*
   * The customized parameters to workflow.
   * */
  @JsonProperty("parameters")
  private Map<String, Object> parameters;

  /*
   * Response content type whether to return the card type.
   * */
  @JsonProperty("enable_card")
  private Boolean enableCard;

  public void enableStream() {
    this.stream = true;
  }

  public void disableStream() {
    this.stream = false;
    this.autoSaveHistory = true;
  }

  public void clearBeforeReq() {
    this.conversationID = null;
  }
}
