package com.coze.openapi.client.workflows.run.model;

import java.util.Map;

import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowEventMessage {
  /** The content of the streamed output message. */
  @JsonProperty("content")
  private String content;

  /** The name of the node that outputs the message, such as the message node or end node. */
  @JsonProperty("node_title")
  private String nodeTitle;

  /**
   * The message ID of this message within the node, starting at 0, for example, the 5th message of
   * the message node.
   */
  @JsonProperty("node_seq_id")
  private String nodeSeqID;

  /** Whether the current message is the last data packet for this node. */
  @JsonProperty("node_is_finish")
  private boolean nodeIsFinish;

  /** Additional fields. */
  @JsonProperty("ext")
  private Map<String, Object> ext;

  public static WorkflowEventMessage fromJson(String data) {
    return Utils.fromJson(data, WorkflowEventMessage.class);
  }
}
