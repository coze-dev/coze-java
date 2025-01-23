package com.coze.openapi.service.service.bots;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.BotAPI;
import com.coze.openapi.client.bots.*;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.bots.model.SimpleBot;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageNumBasedPaginator;
import com.coze.openapi.client.common.pagination.PageRequest;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.service.utils.Utils;

public class BotService {

  private final BotAPI api;

  public BotService(BotAPI api) {
    this.api = api;
  }

  public PageResp<SimpleBot> list(@NotNull ListBotReq req) {
    if (req == null) {
      throw new IllegalArgumentException("req is required");
    }

    Integer pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
    Integer pageSize = req.getPageSize() == null ? 20 : req.getPageSize();
    PageFetcher<SimpleBot> pageFetcher = getSimpleBotPageFetcher(req);

    // 创建分页器
    PageNumBasedPaginator<SimpleBot> paginator = new PageNumBasedPaginator<>(pageFetcher, pageSize);

    // 获取当前页数据
    PageRequest initialRequest = PageRequest.builder().pageNum(pageNum).pageSize(pageSize).build();

    PageResponse<SimpleBot> firstPage = pageFetcher.fetch(initialRequest);

    return PageResp.<SimpleBot>builder()
        .total(firstPage.getTotal())
        .items(firstPage.getData())
        .iterator(paginator)
        .logID(firstPage.getLogID())
        .hasMore(firstPage.isHasMore())
        .build();
  }

  @NotNull
  private PageFetcher<SimpleBot> getSimpleBotPageFetcher(@NotNull ListBotReq req) {
    String spaceID = req.getSpaceID();

    // 创建分页获取器
    PageFetcher<SimpleBot> pageFetcher =
        request -> {
          BaseResponse<ListBotResp> resp =
              Utils.execute(api.list(spaceID, request.getPageNum(), request.getPageSize(), req));
          return PageResponse.<SimpleBot>builder()
              .hasMore(resp.getData().getBots().size() == request.getPageSize())
              .data(resp.getData().getBots())
              .pageNum(request.getPageNum())
              .pageSize(request.getPageSize())
              .total(resp.getData().getTotal())
              .logID(resp.getLogID())
              .build();
        };
    return pageFetcher;
  }

  public RetrieveBotResp retrieve(@NotNull RetrieveBotReq req) {
    BaseResponse<Bot> resp = Utils.execute(api.retrieve(req.getBotID(), req));
    return RetrieveBotResp.builder().bot(resp.getData()).logID(resp.getLogID()).build();
  }

  public CreateBotResp create(@NotNull CreateBotReq req) {
    return Utils.execute(api.create(req, req)).getData();
  }

  public UpdateBotResp update(@NotNull UpdateBotReq req) {
    String logID = Utils.execute(api.update(req, req)).getLogID();
    return UpdateBotResp.builder().logID(logID).build();
  }

  public PublishBotResp publish(@NotNull PublishBotReq req) {
    return Utils.execute(api.publish(req, req)).getData();
  }
}
