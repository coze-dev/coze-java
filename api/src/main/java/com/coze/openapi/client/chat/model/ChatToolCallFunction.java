/* (C)2024 */
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
public class ChatToolCallFunction {
  /** The name of the method. */
  @JsonProperty("name")
  private String name;

  /** The parameters of the method. */
  @JsonProperty("arguments")
  private String arguments;
}
