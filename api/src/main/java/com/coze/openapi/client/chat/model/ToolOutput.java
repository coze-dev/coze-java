/* (C)2024 */
package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolOutput {
  /**
   * The ID for reporting the running results. You can get this ID under the tool_calls field in
   * response of the Chat API.
   */
  @NonNull
  @JsonProperty("tool_call_id")
  private String toolCallID;

  /** The execution result of the tool. */
  @NonNull
  @JsonProperty("output")
  private String output;

  public static ToolOutput of(String toolCallID, String output) {
    return ToolOutput.builder().toolCallID(toolCallID).output(output).build();
  }
}
