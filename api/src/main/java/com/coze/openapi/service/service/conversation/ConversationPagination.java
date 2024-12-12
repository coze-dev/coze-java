package com.coze.openapi.service.service.conversation;

import com.coze.openapi.api.ConversationAPI;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PaginationBase;
import com.coze.openapi.client.connversations.ListConversationResp;
import com.coze.openapi.client.connversations.model.Conversation;
import com.coze.openapi.service.utils.Utils;

public class ConversationPagination extends PaginationBase<Conversation>{
    private final ConversationAPI api;
    private final String botID;
    private final int pageSize;

    public ConversationPagination(ConversationAPI api, String botID, Integer pageSize) {
        this.api = api;
        this.botID = botID;
        if (pageSize != null) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = 50;
        }
    }


    @Override
    protected PageResponse<Conversation> fetchNextPage(String cursor) throws Exception {
        Integer pageCursor = parsePageCursor(cursor);

        ListConversationResp resp = Utils.execute(api.ListConversation(this.botID, pageCursor, this.pageSize)).getData();

        PageResponse<Conversation> data = new PageResponse<>(
            resp.isHasMore(),
            String.valueOf(pageCursor + 1),
            resp.getConversations());
        return data;
    }
}
