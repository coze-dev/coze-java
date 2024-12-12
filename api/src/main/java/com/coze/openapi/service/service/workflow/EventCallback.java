/* (C)2024 */
package com.coze.openapi.service.service.workflow;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.service.service.common.AbstractEventCallback;

import io.reactivex.FlowableEmitter;

public class EventCallback extends AbstractEventCallback<WorkflowEvent> {

  public EventCallback(FlowableEmitter<WorkflowEvent> emitter) {
    super(emitter);
  }

  @Override
  protected boolean processLine(String line, BufferedReader reader, String logID)
      throws IOException {
    if (line.startsWith("id:")) {
      String id = line.substring(3).trim();
      String event = reader.readLine().substring(6).trim();
      String data = reader.readLine().substring(5).trim();

      Map<String, String> eventLine = new HashMap<>();
      eventLine.put("id", id);
      eventLine.put("event", event);
      eventLine.put("data", data);

      WorkflowEvent eventData = WorkflowEvent.parseEvent(eventLine, logID);

      if (eventData != null) {
        emitter.onNext(eventData);
        return eventData.isDone();
      }
    }
    return false;
  }
}
