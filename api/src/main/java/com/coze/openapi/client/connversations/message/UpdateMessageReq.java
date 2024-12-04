package com.coze.openapi.client.connversations.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.client.connversations.message.model.MessageObjectString;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder(builderClassName = "UpdateMessageReqBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMessageReq {
    /*
    * The ID of the conversation.
    * */
    @JsonProperty("conversation_id")
    private String conversationID;

    /*
     * The ID of the message.
     * */
    @JsonProperty("message_id")
    private String messageID;

    /*
     *  The content of the message, supporting pure text, multimodal (mixed input of text, images, files),
     * cards, and various types of content.
     * */
    @JsonProperty("content")
    private String content;

    @JsonProperty("meta_data")
    private Map<String, String> metaData;

    /*
     * The type of message content.
     * */
    @JsonProperty("content_type")
    private MessageContentType contentType;

    public static class UpdateMessageReqBuilder {
        public UpdateMessageReqBuilder ObjectContent(List<MessageObjectString> objects) {
            this.content = Utils.toJson(objects);
            this.contentType = MessageContentType.OBJECT_STRING;
            return this;
        }

        public UpdateMessageReq build(){
            if (this.conversationID == null || this.messageID == null) {
                throw new IllegalStateException("conversationID and messageID are required");
            }
            if (this.content == null && this.metaData == null) {
                throw new IllegalStateException("content and contentType cannot be both null");
            }
            if (this.content != null && this.contentType == null) {
                throw new IllegalStateException("contentType is required when content is not null");
            }
            return new UpdateMessageReq(this.conversationID, this.messageID, this.content, this.metaData, this.contentType);
        }
    }
}
