package com.coze.openapi.client.connversations.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.client.connversations.message.model.MessageObjectString;
import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateMessageReq extends BaseReq {
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

    public static abstract class UpdateMessageReqBuilder<C extends UpdateMessageReq, B extends UpdateMessageReqBuilder<C, B>> extends BaseReqBuilder<C, B> {
        public B objectContent(List<MessageObjectString> objects) {
            this.content = Utils.toJson(objects);
            this.contentType = MessageContentType.OBJECT_STRING;
            return self();
        }
    }
}
