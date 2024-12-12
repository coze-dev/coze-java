package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Event types for chat.
 */
public enum ChatEventType {
    /**
     * Event for creating a conversation, indicating the start of the conversation.
     */
    CONVERSATION_CHAT_CREATED("conversation.chat.created"),

    /**
     * The server is processing the conversation.
     */
    CONVERSATION_CHAT_IN_PROGRESS("conversation.chat.in_progress"),

    /**
     * Incremental message, usually an incremental message when type=answer.
     */
    CONVERSATION_MESSAGE_DELTA("conversation.message.delta"),

    /**
     * The message has been completely replied to. At this point, the streaming package contains the spliced results of all message.delta, and each message is in a completed state.
     */
    CONVERSATION_MESSAGE_COMPLETED("conversation.message.completed"),

    /**
     * The conversation is completed.
     */
    CONVERSATION_CHAT_COMPLETED("conversation.chat.completed"),

    /**
     * This event is used to mark a failed conversation.
     */
    CONVERSATION_CHAT_FAILED("conversation.chat.failed"),

    /**
     * The conversation is interrupted and requires the user to report the execution results of the tool.
     */
    CONVERSATION_CHAT_REQUIRES_ACTION("conversation.chat.requires_action"),

    /**
     * If there is a voice message in the input message, the conversation.audio.delta event will be returned in the
     * streaming response event. The data of this event corresponds to the Message Object. The content_type is audio,
     * and the content is a PCM audio clip with a sampling rate of 24kHz, raw 16 bit, 1 channel, little-endian.
     */
    CONVERSATION_AUDIO_DELTA("conversation.audio.delta"),

    /**
     * Error events during the streaming response process. For detailed explanations of code and msg, please refer to Error codes.
     */
    ERROR("error"),

    /**
     * The streaming response for this session ended normally.
     */
    DONE("done");

    private final String value;

    ChatEventType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ChatEventType fromString(String value) {
        for (ChatEventType type : ChatEventType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ChatEventType: " + value);
    }
} 