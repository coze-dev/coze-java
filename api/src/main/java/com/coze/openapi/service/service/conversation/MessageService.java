package com.coze.openapi.service.service.conversation;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageRequest;
import com.coze.openapi.client.common.pagination.TokenBasedPaginator;
import com.coze.openapi.client.connversations.message.CreateMessageReq;
import com.coze.openapi.client.connversations.message.CreateMessageResp;
import com.coze.openapi.client.connversations.message.DeleteMessageReq;
import com.coze.openapi.client.connversations.message.DeleteMessageResp;
import com.coze.openapi.client.connversations.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.ListMessageResp;
import com.coze.openapi.client.connversations.message.UpdateMessageReq;
import com.coze.openapi.client.connversations.message.UpdateMessageResp;
import com.coze.openapi.client.connversations.message.RetrieveMessageReq;
import com.coze.openapi.client.connversations.message.RetrieveMessageResp;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.utils.Utils;

public class MessageService {
    private final ConversationMessageAPI api;

    public MessageService(ConversationMessageAPI api) {
        this.api = api;
    }

    /*
        Create a message and add it to the specified conversation.

        docs en: https://www.coze.com/docs/developer_guides/create_message
        docs cn: https://www.coze.cn/docs/developer_guides/create_message
    * */
    public CreateMessageResp create(String conversationID, CreateMessageReq req) {
        if (conversationID == null) {
            throw new IllegalArgumentException("conversationID is required");
        }
        BaseResponse<Message> resp = Utils.execute(api.create(conversationID, req, req));
        return CreateMessageResp.builder()
            .message(resp.getData())
            .logID(resp.getLogID())
            .build();
    }

    /*
    Create a message and add it to the specified conversation.

    docs en: https://www.coze.com/docs/developer_guides/create_message
    docs cn: https://www.coze.cn/docs/developer_guides/create_message
    * */
    public CreateMessageResp create(CreateMessageReq req) {
        if (req == null || req.getConversationID() == null) {
            throw new IllegalArgumentException("conversationID is required");
        }
        
        return create(req.getConversationID(), req);
    }

    /*
    *   Get the message list of a specified conversation.

        docs en: https://www.coze.com/docs/developer_guides/list_message
        docs zh: https://www.coze.cn/docs/developer_guides/list_message
    * */
    public PageResp<Message> list(@NotNull ListMessageReq req) {
        if (req == null || req.getConversationID() == null) {
            throw new IllegalArgumentException("conversationID is required");
        }

        String conversationID = req.getConversationID();
        Integer pageSize = req.getLimit();

        // 创建分页获取器
        PageFetcher<Message> pageFetcher = request -> {
            req.setAfterID(request.getPageToken()); // 设置 lastID
            ListMessageResp resp = Utils.execute(api.list(conversationID, req, req));
            
            return PageResponse.<Message>builder()
                .hasMore(resp.getData().size() >= pageSize)
                .data(resp.getData())
                .lastID(resp.getLastID()) // 使用 firstID 作为上一页的 token
                .nextID(resp.getFirstID()) // 使用 lastID 作为下一页的 token
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

        return PageResp.<Message>builder()
            .items(currentPage.getData())
            .iterator(paginator)
            .lastID(currentPage.getNextID())
            .build();
    }


    /*
    *  Get the detailed information of specified message.

        docs en: https://www.coze.com/docs/developer_guides/retrieve_message
        docs zh: https://www.coze.cn/docs/developer_guides/retrieve_message
    * */
    public RetrieveMessageResp retrieve(RetrieveMessageReq req) {
        if (req == null || req.getConversationID() == null || req.getMessageID() == null) {
            throw new IllegalArgumentException("conversationID and messageID are required");
        }
        BaseResponse<Message> resp = Utils.execute(api.retrieve(req.getConversationID(), req.getMessageID(), req));
        return RetrieveMessageResp.builder()
            .message(resp.getData())
            .logID(resp.getLogID())
            .build();
    }

    /*
    *  Modify a message, supporting the modification of message content, additional content, and message type.

        docs en: https://www.coze.com/docs/developer_guides/modify_message
        docs cn: https://www.coze.cn/docs/developer_guides/modify_message
    * */
    public UpdateMessageResp update(UpdateMessageReq req) {
        if (req == null || req.getConversationID() == null || req.getMessageID() == null) {
            throw new IllegalArgumentException("conversationID and messageID are required");
        }
        return Utils.execute(api.update(req.getConversationID(), req.getMessageID(), req, req));
    }

    public DeleteMessageResp delete(DeleteMessageReq req) {
        if (req == null || req.getConversationID() == null || req.getMessageID() == null) {
            throw new IllegalArgumentException("conversationID and messageID are required");
        }
        BaseResponse<Message> resp = Utils.execute(api.delete(req.getConversationID(), req.getMessageID(), req));
        return DeleteMessageResp.builder()
            .message(resp.getData())
            .logID(resp.getLogID())
            .build();
    }


}
