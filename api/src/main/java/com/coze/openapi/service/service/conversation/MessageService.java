package com.coze.openapi.service.service.conversation;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageRequest;
import com.coze.openapi.client.common.pagination.TokenBasedPaginator;
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
        return Utils.execute(api.create(conversationID, req)).getData();
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

        String conversationID = req.getConversationID();
        Integer pageSize = req.getLimit();

        // 创建分页获取器
        PageFetcher<Message> pageFetcher = request -> {
            req.setAfterID(request.getPageToken()); // 设置 lastID
            GetMessageListResp resp = Utils.execute(api.list(conversationID, req));
            
            return PageResponse.<Message>builder()
                .hasMore(resp.getData().size() >= pageSize)
                .data(resp.getData())
                .lastToken(resp.getLastID()) // 使用 firstID 作为上一页的 token
                .nextToken(resp.getFirstID()) // 使用 lastID 作为下一页的 token
                .build();
        };

        // 创建基于 token 的分页器
        TokenBasedPaginator<Message> paginator = new TokenBasedPaginator<>(pageFetcher, req.getLimit());

        // 获取当前页数据
        PageRequest initialRequest = PageRequest.builder()
            .pageSize(pageSize)
            .pageToken(req.getBeforeID())
            .build();
        
        PageResponse<Message> currentPage = pageFetcher.fetch(initialRequest);

        return PageResult.<Message>builder()
            .items(currentPage.getData())
            .iterator(paginator)
            .nextCursor(currentPage.getNextToken()) // 保持与原有接口兼容，使用 nextCursor
            .build();
    }


    public Message retrieve(RetrieveMessageReq req) {
        if (req == null || req.getConversationID() == null || req.getMessageID() == null) {
            throw new IllegalArgumentException("conversationID and messageID are required");
        }
        return Utils.execute(api.retrieve(req.getConversationID(), req.getMessageID())).getData();
    }

    public Message update(UpdateMessageReq req) {
        if (req == null || req.getConversationID() == null || req.getMessageID() == null) {
            throw new IllegalArgumentException("conversationID and messageID are required");
        }
        return Utils.execute(api.update(req.getConversationID(), req.getMessageID(), req)).getMessage();
    }

    public Message delete(DeleteMessageReq req) {
        if (req == null || req.getConversationID() == null || req.getMessageID() == null) {
            throw new IllegalArgumentException("conversationID and messageID are required");
        }
        return Utils.execute(api.delete(req.getConversationID(), req.getMessageID())).getData();
    }


}
