package com.coze.openapi.client.connversations.message;

import java.util.List;
import java.util.Map;

import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.client.connversations.message.model.MessageObjectString;
import com.coze.openapi.client.connversations.message.model.MessageRole;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder(builderClassName = "CreateMessageReqBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageReq {
    /*
     * The ID of the conversation.
     * */
    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;

    /*
     * The entity that sent this message.
     * */
    @NonNull
    private MessageRole role;

    /*
     * The content of the message, supporting pure text, multimodal (mixed input of text, images, files),
     * cards, and various types of content.
     * */
    @NonNull
    private String content;

    /*
     * The type of message content.
     * */
    @NonNull
    @JsonProperty("content_type")
    private MessageContentType contentType;

    /*
     * Additional information when creating a message, and this additional information will also be
     * returned when retrieving messages.
     * */
    @JsonProperty("meta_data")
    private Map<String, String> metadata;
        

    public static class CreateMessageReqBuilder {
        public CreateMessageReqBuilder objectContent(List<MessageObjectString> objects) {
            this.content = Utils.toJson(objects);
            this.contentType = MessageContentType.OBJECT_STRING;
            return this;
        }
    }


}
