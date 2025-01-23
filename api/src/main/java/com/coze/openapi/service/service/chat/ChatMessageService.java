package com.coze.openapi.service.service.chat;

import java.util.List;

import com.coze.openapi.api.ChatMessageAPI;
import com.coze.openapi.client.chat.message.ListMessageReq;
import com.coze.openapi.client.chat.message.ListMessageResp;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.utils.Utils;

public class ChatMessageService {

  private final ChatMessageAPI chatMessageApi;

  public ChatMessageService(ChatMessageAPI chatMessageApi) {
    this.chatMessageApi = chatMessageApi;
  }

  public ListMessageResp list(ListMessageReq req) {
    BaseResponse<List<Message>> resp =
        Utils.execute(chatMessageApi.list(req.getConversationID(), req.getChatID(), req));
    return ListMessageResp.builder().logID(resp.getLogID()).messages(resp.getData()).build();
  }
}
