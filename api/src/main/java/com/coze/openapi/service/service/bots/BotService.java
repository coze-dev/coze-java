package com.coze.openapi.service.service.bots;

import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.BotAPI;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.bots.model.SimpleBot;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageIterator;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.bots.CreateBotReq;
import com.coze.openapi.client.bots.CreateBotResp;
import com.coze.openapi.client.bots.ListBotReq;
import com.coze.openapi.client.bots.ListBotResp;
import com.coze.openapi.client.bots.PublishBotReq;
import com.coze.openapi.client.bots.PublishBotResp;
import com.coze.openapi.client.bots.UpdateBotReq;
import com.coze.openapi.service.utils.Utils;

public class BotService {

    private final String defaultChannelID = "1024";
    private final BotAPI api;

    public BotService(BotAPI api) {
        this.api = api;
    }

    public PageResult<SimpleBot> list(@NotNull ListBotReq req) {
        Integer pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        Integer pageSize = req.getPageSize() == null ? 20 : req.getPageSize();
        // 获取当前页数据
        BaseResponse<ListBotResp> resp = Utils.execute(api.ListBots(req.getSpaceID(), pageNum, pageSize));
        // 生成分页器
        BotPagination pagination = new BotPagination(api, pageSize, req.getSpaceID());
        // 构建当前页数据
        Boolean hasMore = resp.getData().getBots().size() == pageSize;

        PageResponse<SimpleBot> pageResponse = PageResponse.<SimpleBot>builder()
            .hasMore(hasMore)
            .nextCursor(String.valueOf(pageNum + 1))
            .data(resp.getData().getBots())
            .build();

        return PageResult.<SimpleBot>builder()
            .total(resp.getData().getTotal())
            .items(resp.getData().getBots())
            .iterator(new PageIterator<>(pagination, pageResponse))
            .hasMore(hasMore)
            .build();
    }

    public Bot retrieve(@NotNull String botID) {
        return Utils.execute(api.GetBotInfo(botID)).getData();
    }

    public CreateBotResp create(@NotNull CreateBotReq req ) {
        return Utils.execute(api.CreateBot(req)).getData();
    }

    public void update(@NotNull String botID, @NotNull UpdateBotReq req) {
        req.setBotID(botID);
        Utils.execute(api.UpdateBot(req));
    }
    
    public PublishBotResp publish(@NotNull String botID) {
        PublishBotReq.PublishBotReqBuilder builder = PublishBotReq.builder();
        builder.botID(botID).connectorIDs(Arrays.asList(defaultChannelID));
        return Utils.execute(api.PublishBot(builder.build())).getData();
    }
}
