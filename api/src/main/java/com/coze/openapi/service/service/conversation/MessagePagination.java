package com.coze.openapi.service.service.conversation;

import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.client.common.Sort;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PaginationBase;
import com.coze.openapi.client.connversations.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.GetMessageListResp;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.utils.Utils;

public class MessagePagination extends PaginationBase<Message>{
    private final ConversationMessageAPI api;
    private final String conversationID;
    private final String chatID;
    private final int pageSize;
    private final String afterID;
    private final String beforeID;
    private final Sort sort;

    public MessagePagination(ConversationMessageAPI api, String conversationID, ListMessageReq req) {
        this.api = api;
        this.conversationID = conversationID;
        this.chatID = req.getChatID();
        if (req.getLimit() != null) {  
            this.pageSize = req.getLimit();
        } else {
            this.pageSize = 20;
        }
        this.afterID = req.getAfterID();
        this.beforeID = req.getBeforeID();
        if (req.getSort() != null) {
            this.sort = req.getSort();
        } else {
            this.sort = Sort.DESC;
        }
    }


    // todo: 当前仅支持了向后翻页，还没有支持向前翻页，是否支持向前翻页
    @Override
    protected PageResponse<Message> fetchNextPage(String cursor) throws Exception {
        String pageCursor = cursor == null ? this.afterID : cursor;

        ListMessageReq req = ListMessageReq.builder()
            .chatID(this.chatID)
            .conversationID(this.conversationID)
            .afterID(pageCursor)
            .beforeID(this.beforeID)
            .sort(this.sort)
            .limit(this.pageSize)
            .build();

        GetMessageListResp resp = Utils.execute(api.GetMessageList(this.conversationID, req));

        // BaseResponse<ListBotResp> resp = Utils.execute(api.ListBots(this.spaceID, pageCursor, pageSize));
        PageResponse<Message> data = new PageResponse<>(
            resp.isHasMore(),
            resp.getLastID(),
            resp.getData());
        return data;
    }
}
