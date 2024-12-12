package com.coze.openapi.client.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.coze.openapi.client.connversations.message.model.Message;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatPoll {
    @JsonProperty("chat")
    private Chat chat;

    @JsonProperty("messages")
    private List<Message> messages;
} 