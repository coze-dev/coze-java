package com.coze.openapi.client.workflows.chat;

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
public class WorkflowChatReq extends BaseReq {
  // The ID of the workflow that the API interacts with.
  @JsonProperty("workflow_id")
  @NonNull
  private String workflowID;

  // Additional information for the conversation. You can pass the user's query for this
  // conversation through this field. The array length is limited to 50, meaning up to 50 messages
  // can be input.
  @JsonProperty("additional_messages")
  private List<Message> additionalMessages;

  // The parameters for the workflow.
  @JsonProperty("parameters")
  private Map<String, Object> parameters;

  // The ID of the app that the API interacts with.
  @JsonProperty("app_id")
  private String appID;

  // The ID of the bot that the API interacts with.
  @JsonProperty("bot_id")
  private String botID;

  // Indicate which conversation the chat is taking place in.
  @JsonProperty("conversation_id")
  private String conversationID;

  @JsonProperty("ext")
  private Map<String, String> ext;
}
