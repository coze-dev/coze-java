package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequiredAction {
  /** The type of additional operation, with the enum value of submit_tool_outputs. */
  @JsonProperty("type")
  private ChatRequiredActionType type;

  /**
   * Details of the results that need to be submitted, uploaded through the submission API, and the
   * chat can continue afterward.
   */
  @JsonProperty("submit_tool_outputs")
  private ChatSubmitToolOutputs submitToolOutputs;
}
