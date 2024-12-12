/* (C)2024 */
package com.coze.openapi.service.service.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.service.service.common.AbstractEventCallback;

import io.reactivex.FlowableEmitter;

public class EventCallback extends AbstractEventCallback<ChatEvent> {

  public EventCallback(FlowableEmitter<ChatEvent> emitter) {
    super(emitter);
  }

  @Override
  protected boolean processLine(String line, BufferedReader reader, String logID)
      throws IOException {
    if (line.startsWith("event:")) {
      String event = line.substring(6).trim();
      String data = reader.readLine().substring(5).trim();

      Map<String, String> eventLine = new HashMap<>();
      eventLine.put("event", event);
      eventLine.put("data", data);

      ChatEvent eventData = ChatEvent.parseEvent(eventLine, logID);

      emitter.onNext(eventData);
      return eventData.isDone();
    }
    return false;
  }
}
