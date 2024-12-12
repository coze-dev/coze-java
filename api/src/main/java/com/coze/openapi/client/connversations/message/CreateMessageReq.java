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
    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;
    @NonNull
    private MessageRole role;
    @NonNull
    private String content;
    @NonNull
    @JsonProperty("content_type")
    private MessageContentType contentType;
    private Map<String, String> metadata;
        

    public static class CreateMessageReqBuilder {
        public CreateMessageReqBuilder ObjectContent(List<MessageObjectString> objects) {
            this.content = Utils.toJson(objects);
            this.contentType = MessageContentType.OBJECT_STRING;
            return this;
        }
    }


}
