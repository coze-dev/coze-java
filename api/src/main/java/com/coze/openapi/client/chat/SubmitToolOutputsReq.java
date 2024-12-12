package com.coze.openapi.client.chat;

import java.util.List;

import com.coze.openapi.client.chat.model.ToolOutput;
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
public class SubmitToolOutputsReq {

    @NonNull
    @JsonProperty("conversation_id")
    private String conversationID;
    @NonNull
    @JsonProperty("chat_id")
    private String chatID;

    @NonNull
    @JsonProperty("tool_outputs")
    private List<ToolOutput> toolOutputs;

    @JsonProperty("stream")
    private Boolean stream;

    public void enableStream() {
        this.stream = true;
    }

    public void disableStream() {
        this.stream = false;
    }
    
    public void clearBeforeReq(){
        this.chatID = null;
        this.conversationID = null;
    }
}
