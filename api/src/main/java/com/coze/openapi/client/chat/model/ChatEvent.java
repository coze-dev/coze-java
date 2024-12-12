package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.exception.CozeApiExcetion;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatEvent {
    @JsonProperty("event")
    private ChatEventType event;

    @JsonProperty("chat")
    private Chat chat;

    @JsonProperty("message")
    private Message message;

    public static ChatEvent parseEvent(Map<String, String> eventLine, String logID) {
        ChatEventType eventType = ChatEventType.fromString(eventLine.get("event"));
        String data = eventLine.get("data");
        switch (eventType) {
            case DONE:
                return new ChatEvent(eventType, null, null);
            case ERROR:
                throw new CozeApiExcetion(0, data, logID);
            case CONVERSATION_MESSAGE_DELTA:
                case CONVERSATION_MESSAGE_COMPLETED:
                case CONVERSATION_AUDIO_DELTA:
                return new ChatEvent(eventType, null, Message.fromJson(data));
            case CONVERSATION_CHAT_CREATED:
            case CONVERSATION_CHAT_IN_PROGRESS:
            case CONVERSATION_CHAT_COMPLETED:
            case CONVERSATION_CHAT_FAILED:
            case CONVERSATION_CHAT_REQUIRES_ACTION:
                return new ChatEvent(eventType, Chat.fromJson(data), null);
            default:
                break;
        }
        throw new RuntimeException("Unknown event type: " + eventType);
    }

    public boolean isDone() {
        return ChatEventType.DONE.equals(this.event) || ChatEventType.CONVERSATION_CHAT_FAILED.equals(this.event);
    }
} 