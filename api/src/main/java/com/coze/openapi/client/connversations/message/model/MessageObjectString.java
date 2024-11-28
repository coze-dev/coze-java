package com.coze.openapi.client.connversations.message.model;

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
public class MessageObjectString {

    @JsonProperty("type")
    private MessageObjectStringType type;

    @JsonProperty("text")
    private String text;

    @JsonProperty("file_id")
    private String fileID;

    @JsonProperty("file_url")
    private String fileUrl;

    public static MessageObjectString buildText(String text) {
        return MessageObjectString.builder()
                .type(MessageObjectStringType.TEXT)
                .text(text)
                .build();
    }

    public static MessageObjectString buildImage(String fileUrl) {
        return buildImage(null, fileUrl);
    }

    public static MessageObjectString buildImage(String fileID, String fileUrl) {
        if (fileID == null && fileUrl == null) {
            throw new IllegalArgumentException("file_id or file_url must be specified");
        }
        return MessageObjectString.builder()
                .type(MessageObjectStringType.IMAGE)
                .fileID(fileID)
                .fileUrl(fileUrl)
                .build();
    }

    public static MessageObjectString buildFile(String fileID, String fileUrl) {
        if (fileID == null && fileUrl == null) {
            throw new IllegalArgumentException("file_id or file_url must be specified");
        }
        return MessageObjectString.builder()
                .type(MessageObjectStringType.FILE)
                .fileID(fileID)
                .fileUrl(fileUrl)
                .build();
    }

    public static MessageObjectString buildAudio(String fileID, String fileUrl) {
        if (fileID == null && fileUrl == null) {
            throw new IllegalArgumentException("file_id or file_url must be specified");
        }
        return MessageObjectString.builder()
                .type(MessageObjectStringType.AUDIO)
                .fileID(fileID)
                .fileUrl(fileUrl)
                .build();
    }

    public String toJson() {
        return Utils.toJson(this);
    }
} 
