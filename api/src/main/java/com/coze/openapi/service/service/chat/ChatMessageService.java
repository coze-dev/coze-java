package com.coze.openapi.service.service.chat;

import java.util.List;

import com.coze.openapi.api.ChatMessageAPI;
import com.coze.openapi.client.chat.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.utils.Utils;

public class ChatMessageService {


    private final ChatMessageAPI chatMessageApi;

    public ChatMessageService(ChatMessageAPI chatMessageApi) {
        this.chatMessageApi = chatMessageApi;
    }

     public List<Message> list(ListMessageReq req) {
        return Utils.execute(chatMessageApi.listMessage(req.getConversationID(), req.getChatID())).getData();
    }
}
