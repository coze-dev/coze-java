package com.coze.openapi.service.service.bots;

import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.BotAPI;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.bots.model.SimpleBot;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageNumBasedPaginator;
import com.coze.openapi.client.common.pagination.PageRequest;
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
        if (req == null) {
            throw new IllegalArgumentException("req is required");
        }

        Integer pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        Integer pageSize = req.getPageSize() == null ? 20 : req.getPageSize();
        String spaceID = req.getSpaceID();

        // 创建分页获取器
        PageFetcher<SimpleBot> pageFetcher = request -> {
            BaseResponse<ListBotResp> resp = Utils.execute(api.list(spaceID, request.getPageNum(), request.getPageSize()));
            
            return PageResponse.<SimpleBot>builder()
                .hasMore(resp.getData().getBots().size() == request.getPageSize())
                .data(resp.getData().getBots())
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .total(resp.getData().getTotal())
                .build();
        };

        // 创建分页器
        PageNumBasedPaginator<SimpleBot> paginator = new PageNumBasedPaginator<>(pageFetcher, pageSize);

        // 获取当前页数据
        PageRequest initialRequest = PageRequest.builder()
            .pageNum(pageNum)
            .pageSize(pageSize)
            .build();
        
        PageResponse<SimpleBot> firstPage = pageFetcher.fetch(initialRequest);

        return PageResult.<SimpleBot>builder()
            .total(firstPage.getTotal())
            .items(firstPage.getData())
            .iterator(paginator)
            .hasMore(firstPage.isHasMore())
            .build();
    }


    public Bot retrieve(@NotNull String botID) {
        return Utils.execute(api.retrieve(botID)).getData();
    }

    public CreateBotResp create(@NotNull CreateBotReq req ) {
        return Utils.execute(api.create(req)).getData();
    }

    public void update(@NotNull String botID, @NotNull UpdateBotReq req) {
        req.setBotID(botID);
        Utils.execute(api.update(req));
    }
    
    public PublishBotResp publish(@NotNull String botID) {
        PublishBotReq.PublishBotReqBuilder builder = PublishBotReq.builder();
        builder.botID(botID).connectorIDs(Arrays.asList(defaultChannelID));
        return Utils.execute(api.publish(builder.build())).getData();
    }
}
