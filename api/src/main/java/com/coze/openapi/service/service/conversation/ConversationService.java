package com.coze.openapi.service.service.conversation;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.ConversationAPI;
import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.client.connversations.GetConversationResp;
import com.coze.openapi.client.connversations.ListConversationReq;
import com.coze.openapi.client.connversations.ListConversationResp;
import com.coze.openapi.client.connversations.model.Conversation;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.connversations.ClearConversationReq;
import com.coze.openapi.client.connversations.ClearConversationResp;
import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.CreateConversationResp;
import com.coze.openapi.client.connversations.GetConversationReq;
import com.coze.openapi.service.utils.Utils;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageNumBasedPaginator;
import com.coze.openapi.client.common.pagination.PageRequest;

public class ConversationService {
    private final ConversationAPI api;
    private final MessageService messageApi;

    public ConversationService(ConversationAPI api, ConversationMessageAPI messageApi) {
        this.api = api;
        this.messageApi = new MessageService(messageApi);
    }

    public GetConversationResp retrieve(GetConversationReq req) {
        return Utils.execute(api.retrieve(req.getConversationID())).getData();
    }

    public CreateConversationResp create(CreateConversationReq req) {
        return Utils.execute(api.create(req)).getData();
    }

    public ClearConversationResp clear(ClearConversationReq req) {
        return Utils.execute(api.clear(req.getConversationID())).getData();
    }

    public PageResult<Conversation> list(@NotNull ListConversationReq req) {
        if (req == null || req.getBotID() == null) {
            throw new IllegalArgumentException("botID is required");
        }

        Integer pageNum = req.getPageNum();
        Integer pageSize = req.getPageSize();
        String botID = req.getBotID();

        // 创建分页获取器
        PageFetcher<Conversation> pageFetcher = request -> {
            ListConversationResp resp = Utils.execute(api.list(botID, request.getPageNum(), request.getPageSize())).getData();
            
            return PageResponse.<Conversation>builder()
                .hasMore(resp.isHasMore())
                .data(resp.getConversations())
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .build();
        };

        // 创建分页器
        PageNumBasedPaginator<Conversation> paginator = new PageNumBasedPaginator<>(pageFetcher, pageSize);

        // 获取当前页数据
        PageRequest initialRequest = PageRequest.builder()
            .pageNum(pageNum)
            .pageSize(pageSize)
            .build();
        
        PageResponse<Conversation> firstPage = pageFetcher.fetch(initialRequest);

        return PageResult.<Conversation>builder()
            .items(firstPage.getData())
            .iterator(paginator)
            .hasMore(firstPage.isHasMore())
            .build();
    }

    public MessageService messages() {
        return messageApi;
    }
}
