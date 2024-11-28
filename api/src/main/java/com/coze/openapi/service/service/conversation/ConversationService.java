package com.coze.openapi.service.service.conversation;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.ConversationAPI;
import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.client.connversations.GetConversationResp;
import com.coze.openapi.client.connversations.ListConversationReq;
import com.coze.openapi.client.connversations.ListConversationResp;
import com.coze.openapi.client.connversations.model.Conversation;
import com.coze.openapi.client.common.pagination.PageIterator;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.connversations.ClearConversationReq;
import com.coze.openapi.client.connversations.ClearConversationResp;
import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.CreateConversationResp;
import com.coze.openapi.client.connversations.GetConversationReq;
import com.coze.openapi.service.utils.Utils;

public class ConversationService {
    private final ConversationAPI api;
    private final MessageService messageApi;

    public ConversationService(ConversationAPI api, ConversationMessageAPI messageApi) {
        this.api = api;
        this.messageApi = new MessageService(messageApi);
    }

    public GetConversationResp retrieve(GetConversationReq req) {
        return Utils.execute(api.RetrieveConversation(req.getConversationID())).getData();
    }

    public CreateConversationResp create(CreateConversationReq req) {
        return Utils.execute(api.CreateConversation(req)).getData();
    }

    public ClearConversationResp clear(ClearConversationReq req) {
        return Utils.execute(api.ClearConversation(req.getConversationID())).getData();
    }

    public PageResult<Conversation> list(@NotNull ListConversationReq req) {
        if (req == null || req.getBotID() == null) {
            throw new IllegalArgumentException("botID is required");
        }
        ListConversationResp resp = Utils.execute(api.ListConversation(req.getBotID(), req.getPageNum(), req.getPageSize())).getData();
        // 生成分页器
        ConversationPage pagination = new ConversationPage(api,req.getBotID(), req.getPageSize());
        // 构建当前页数据
        PageResponse<Conversation> pageResponse = PageResponse.<Conversation>builder()
            .hasMore(resp.isHasMore())
            .nextCursor(String.valueOf(req.getPageNum() + 1))
            .data(resp.getConversations())
            .build();


        return PageResult.<Conversation>builder()
            .items(resp.getConversations())
            .iterator(new PageIterator<>(pagination, pageResponse))
            .hasMore(resp.isHasMore())
            .nextCursor(String.valueOf(req.getPageNum() + 1))
            .build();
    }

    public MessageService messages() {
        return messageApi;
    }
}
