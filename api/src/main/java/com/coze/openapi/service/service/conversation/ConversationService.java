package com.coze.openapi.service.service.conversation;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.ConversationAPI;
import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.client.connversations.ListConversationReq;
import com.coze.openapi.client.connversations.ListConversationResp;
import com.coze.openapi.client.connversations.model.Conversation;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.connversations.ClearConversationReq;
import com.coze.openapi.client.connversations.ClearConversationResp;
import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.CreateConversationResp;
import com.coze.openapi.client.connversations.RetrieveConversationReq;
import com.coze.openapi.client.connversations.RetrieveConversationResp;
import com.coze.openapi.service.utils.Utils;
import com.coze.openapi.client.common.BaseResponse;
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

    /*
        Get the information of specific conversation.

        docs en: https://www.coze.com/docs/developer_guides/retrieve_conversation
        docs cn: https://www.coze.cn/docs/developer_guides/retrieve_conversation
    * */
    public RetrieveConversationResp retrieve(RetrieveConversationReq req) {
        BaseResponse<Conversation> resp = Utils.execute(api.retrieve(req.getConversationID(), req));
        return RetrieveConversationResp.builder()
            .conversation(resp.getData())
            .logID(resp.getLogID())
            .build();
    }

    /*
        Create a conversation.
        Conversation is an interaction between a bot and a user, including one or more messages.

        docs en: https://www.coze.com/docs/developer_guides/create_conversation
        docs zh: https://www.coze.cn/docs/developer_guides/create_conversation
    * */
    public CreateConversationResp create(CreateConversationReq req) {
        BaseResponse<Conversation> resp = Utils.execute(api.create(req, req));
        return CreateConversationResp.builder()
            .conversation(resp.getData())
            .logID(resp.getLogID())
            .build();
    }


    public ClearConversationResp clear(ClearConversationReq req) {
        return Utils.execute(api.clear(req.getConversationID(), req)).getData();
    }

    public PageResp<Conversation> list(@NotNull ListConversationReq req) {
        if (req == null || req.getBotID() == null) {
            throw new IllegalArgumentException("botID is required");
        }

        Integer pageNum = req.getPageNum();
        Integer pageSize = req.getPageSize();
        String botID = req.getBotID();

        // 创建分页获取器
        PageFetcher<Conversation> pageFetcher = request -> {
            ListConversationResp resp = Utils.execute(api.list(botID, request.getPageNum(), request.getPageSize(), req)).getData();
            
            return PageResponse.<Conversation>builder()
                .hasMore(resp.isHasMore())
                .data(resp.getConversations())
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .logID(resp.getLogID())
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

        return PageResp.<Conversation>builder()
            .items(firstPage.getData())
            .iterator(paginator)
            .logID(firstPage.getLogID())
            .hasMore(firstPage.isHasMore())
            .build();
    }

    public MessageService messages() {
        return messageApi;
    }
}
