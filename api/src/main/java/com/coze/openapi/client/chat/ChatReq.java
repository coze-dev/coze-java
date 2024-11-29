package com.coze.openapi.client.chat;

import java.util.List;
import java.util.Map;

import com.coze.openapi.client.connversations.message.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ChatReq {

    @JsonProperty("conversation_id")
    private String conversationID;

    @NonNull
    @JsonProperty("bot_id")
    private String botID;
    
    @NonNull
    @JsonProperty("user_id")
    private String userID;

    @JsonProperty("additional_messages")
    private List<Message> messages;

    @JsonProperty("stream")
    private Boolean stream;

    @JsonProperty("custom_variables")
    private Map<String, String> customVariables;

    @JsonProperty("auto_save_history")
    private Boolean autoSaveHistory;

    @JsonProperty("meta_data")
    private Map<String, String> metaData;

    @JsonProperty("extra_params")
    private Map<String, String> extraParams;

    public void enableStream() {
        this.stream = true;
    }

    public void disableStream() {
        this.stream = false;
        this.autoSaveHistory = true;
    }

    public void clearBeforeReq() {
        this.conversationID = null;
    }

}
