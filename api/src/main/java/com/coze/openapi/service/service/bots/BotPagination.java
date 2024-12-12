package com.coze.openapi.service.service.bots;

import com.coze.openapi.api.BotAPI;
import com.coze.openapi.client.bots.ListBotResp;
import com.coze.openapi.client.bots.model.SimpleBot;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PaginationBase;
import com.coze.openapi.service.utils.Utils;

public class BotPagination extends PaginationBase<SimpleBot>{
    private final BotAPI api;
    private final int pageSize ;
    private final String spaceID;

    public BotPagination(BotAPI api, String spaceID) {
        this.api = api;
        this.pageSize = 20;
        this.spaceID = spaceID;
    }

    public BotPagination(BotAPI api, int pageSize, String spaceID) {
        this.api = api;
        this.pageSize = pageSize;
        this.spaceID = spaceID;
    }

    @Override
    protected PageResponse<SimpleBot> fetchNextPage(String cursor) throws Exception {
        Integer pageCursor = parsePageCursor(cursor);
        BaseResponse<ListBotResp> resp = Utils.execute(api.ListBots(this.spaceID, pageCursor, pageSize));
        PageResponse<SimpleBot> data = new PageResponse<>(
            resp.getData().getBots().size() == pageSize,
            String.valueOf(pageCursor + 1),
            resp.getData().getBots()
        );
        return data;
    }
}
