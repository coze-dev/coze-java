package com.coze.openapi.service.service.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.coze.openapi.client.chat.model.ChatEvent;

import io.reactivex.FlowableEmitter;

public class ChatEventCallback extends AbstractEventCallback<ChatEvent> {

    public ChatEventCallback(FlowableEmitter<ChatEvent> emitter) {
        super(emitter);
    }

    @Override
    protected boolean processLine(String line, BufferedReader reader, String logID) throws IOException {
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
