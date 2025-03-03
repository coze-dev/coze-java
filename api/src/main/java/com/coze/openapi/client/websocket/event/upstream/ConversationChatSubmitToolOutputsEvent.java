package com.coze.openapi.client.websocket.event.upstream;

import java.util.List;

import com.coze.openapi.client.chat.model.ToolOutput;
import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
// event_type: conversation.chat.submit_tool_outputs
public class ConversationChatSubmitToolOutputsEvent extends BaseEvent {
  @JsonProperty("event_type")
  @Builder.Default
  private final String eventType = EventType.CONVERSATION_CHAT_SUBMIT_TOOL_OUTPUTS;

  @JsonProperty("data")
  private Data data;

  @lombok.Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Data {
    @JsonProperty("chat_id")
    private String chatID;

    @JsonProperty("tool_outputs")
    private List<ToolOutput> toolOutputs;
  }
}
