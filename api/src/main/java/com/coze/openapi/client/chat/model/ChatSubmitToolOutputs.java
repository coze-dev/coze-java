/* (C)2024 */
package com.coze.openapi.client.chat.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSubmitToolOutputs {
  /** Details of the specific reported information. */
  @JsonProperty("tool_calls")
  private List<ChatToolCall> toolCalls;
}
