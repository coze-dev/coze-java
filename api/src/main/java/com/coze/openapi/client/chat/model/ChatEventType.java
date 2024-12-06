package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Event types for chat.
 */
@Getter
public class ChatEventType {
    /**
     * Event for creating a conversation, indicating the start of the conversation.
     */
    public static final ChatEventType CONVERSATION_CHAT_CREATED = new ChatEventType("conversation.chat.created");

    /**
     * The server is processing the conversation.
     */
    public static final ChatEventType CONVERSATION_CHAT_IN_PROGRESS = new ChatEventType("conversation.chat.in_progress");

    /**
     * Incremental message, usually an incremental message when type=answer.
     */
    public static final ChatEventType CONVERSATION_MESSAGE_DELTA = new ChatEventType("conversation.message.delta");

    /**
     * The message has been completely replied to.
     */
    public static final ChatEventType CONVERSATION_MESSAGE_COMPLETED = new ChatEventType("conversation.message.completed");

    /**
     * The conversation is completed.
     */
    public static final ChatEventType CONVERSATION_CHAT_COMPLETED = new ChatEventType("conversation.chat.completed");

    /**
     * This event is used to mark a failed conversation.
     */
    public static final ChatEventType CONVERSATION_CHAT_FAILED = new ChatEventType("conversation.chat.failed");

    /**
     * The conversation is interrupted and requires the user to report the execution results of the tool.
     */
    public static final ChatEventType CONVERSATION_CHAT_REQUIRES_ACTION = new ChatEventType("conversation.chat.requires_action");

    /**
     * Audio delta event
     */
    public static final ChatEventType CONVERSATION_AUDIO_DELTA = new ChatEventType("conversation.audio.delta");

    /**
     * Error events during the streaming response process.
     */
    public static final ChatEventType ERROR = new ChatEventType("error");

    /**
     * The streaming response for this session ended normally.
     */
    public static final ChatEventType DONE = new ChatEventType("done");

    private final String value;

    private ChatEventType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ChatEventType fromString(String value) {
        ChatEventType[] types = {
            CONVERSATION_CHAT_CREATED, CONVERSATION_CHAT_IN_PROGRESS,
            CONVERSATION_MESSAGE_DELTA, CONVERSATION_MESSAGE_COMPLETED,
            CONVERSATION_CHAT_COMPLETED, CONVERSATION_CHAT_FAILED,
            CONVERSATION_CHAT_REQUIRES_ACTION, CONVERSATION_AUDIO_DELTA,
            ERROR, DONE
        };
        
        for (ChatEventType type : types) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return new ChatEventType(value);
    }
} 