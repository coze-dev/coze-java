package com.coze.openapi.client.websocket.event.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChatConfig {
  @JsonProperty("conversation_id")
  private String conversationId;

  @JsonProperty("user_id")
  private String userId;

  @JsonProperty("meta_data")
  private Map<String, String> metaData;

  @JsonProperty("custom_variables")
  private Map<String, String> customVariables;

  @JsonProperty("extra_params")
  private Map<String, String> extraParams;

  @JsonProperty("auto_save_history")
  private Boolean autoSaveHistory;
}
