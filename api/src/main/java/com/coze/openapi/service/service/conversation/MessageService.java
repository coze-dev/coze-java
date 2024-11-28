package com.coze.openapi.service.service.conversation;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.client.common.pagination.PageIterator;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.connversations.message.CreateMessageReq;
import com.coze.openapi.client.connversations.message.DeleteMessageReq;
import com.coze.openapi.client.connversations.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.GetMessageListResp;
import com.coze.openapi.client.connversations.message.UpdateMessageReq;
import com.coze.openapi.client.connversations.message.RetrieveMessageReq;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.utils.Utils;

public class MessageService {
    private final ConversationMessageAPI api;

    public MessageService(ConversationMessageAPI api) {
        this.api = api;
    }

    public Message create(String conversationID, CreateMessageReq req) {
        if (conversationID == null) {
            throw new IllegalArgumentException("conversationID is required");
        }
        return Utils.execute(api.CreateMessage(conversationID, req)).getData(); 
    }
    
    public Message create(CreateMessageReq req) {
        if (req == null || req.getConversationID() == null) {
            throw new IllegalArgumentException("conversationID is required");
        }
        return create(req.getConversationID(), req);
    }

    public PageResult<Message> list(@NotNull ListMessageReq req) {
        if (req == null || req.getConversationID() == null) {
            throw new IllegalArgumentException("conversationID is required");
        }
        GetMessageListResp resp = Utils.execute(api.GetMessageList(req.getConversationID(), req));
        // 生成分页器
        MessagePage pagination = new MessagePage(api,req.getConversationID(), req);
        // 构建当前页数据
        PageResponse<Message> pageResponse = PageResponse.<Message>builder()
            .hasMore(resp.isHasMore())
            .nextCursor(resp.getLastID())
            .data(resp.getData())
            .build();


        return PageResult.<Message>builder()
            .items(resp.getData())
            .iterator(new PageIterator<>(pagination, pageResponse))
            .hasMore(resp.isHasMore())
            .nextCursor(resp.getLastID())
            .build();
    }


    public Message retrieve(RetrieveMessageReq req) {
        if (req == null || req.getConversationID() == null || req.getMessageID() == null) {
            throw new IllegalArgumentException("conversationID and messageID are required");
        }
        return Utils.execute(api.RetrieveConversation(req.getConversationID(), req.getMessageID())).getData();
    }

    public Message update(UpdateMessageReq req) {
        if (req == null || req.getConversationID() == null || req.getMessageID() == null) {
            throw new IllegalArgumentException("conversationID and messageID are required");
        }
        return Utils.execute(api.UpdateMessage(req.getConversationID(), req.getMessageID(), req)).getMessage();
    }

    public Message delete(DeleteMessageReq req) {
        if (req == null || req.getConversationID() == null || req.getMessageID() == null) {
            throw new IllegalArgumentException("conversationID and messageID are required");
        }
        return Utils.execute(api.DeleteMessage(req.getConversationID(), req.getMessageID())).getData();
    }


}
