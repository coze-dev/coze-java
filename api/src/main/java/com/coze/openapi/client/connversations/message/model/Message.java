package com.coze.openapi.client.connversations.message.model;

import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @JsonProperty("role")
    private MessageRole role;

    @JsonProperty("type")
    @Builder.Default
    private MessageType type = MessageType.UNKNOWN;

    @JsonProperty("content")
    private String content;

    @JsonProperty("content_type")
    private MessageContentType contentType;

    @JsonProperty("meta_data")
    private Map<String, String> metaData;

    @JsonProperty("id")
    private String id;

    @JsonProperty("conversation_id")
    private String conversationID;

    @JsonProperty("section_id")
    private String sectionID;

    @JsonProperty("bot_id")
    private String botID;

    @JsonProperty("chat_id")
    private String chatID;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("updated_at")
    private Long updatedAt;

    public static Message buildUserQuestionText(String content) {
        return buildUserQuestionText(content, null);
    }

    public static Message buildUserQuestionText(String content, Map<String, String> metaData) {
        return Message.builder()
                .role(MessageRole.USER)
                .type(MessageType.QUESTION)
                .content(content)
                .contentType(MessageContentType.TEXT)
                .metaData(metaData)
                .build();
    }

    public static Message buildUserQuestionObjects(List<MessageObjectString> objects) {
       return buildUserQuestionObjects(objects, null);
    }

    public static Message buildUserQuestionObjects(List<MessageObjectString> objects, Map<String, String> metaData) {
        return Message.builder()
                .role(MessageRole.USER)
                .type(MessageType.QUESTION)
                .content(Utils.toJson(objects))
                .contentType(MessageContentType.OBJECT_STRING)
                .metaData(metaData)
                .build();
    }

    public static Message buildAssistantAnswer(String content) {
        return buildAssistantAnswer(content, null);
    }

    public static Message buildAssistantAnswer(String content, Map<String, String> metaData) {
        return Message.builder()
                .role(MessageRole.ASSISTANT)
                .type(MessageType.ANSWER)
                .content(content)
                .contentType(MessageContentType.TEXT)
                .metaData(metaData)
                .build();
    }


    public static Message fromJson(String json) {
        return Utils.fromJson(json, Message.class);
    }
} 