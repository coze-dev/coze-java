package com.coze.openapi.service.service.bots;

import com.coze.openapi.api.BotAPI;
import com.coze.openapi.client.bots.CreateBotReq;
import com.coze.openapi.client.bots.CreateBotResp;
import com.coze.openapi.client.bots.ListBotReq;
import com.coze.openapi.client.bots.ListBotResp;
import com.coze.openapi.client.bots.ListBotV2Req;
import com.coze.openapi.client.bots.ListBotV2Resp;
import com.coze.openapi.client.bots.PublishBotReq;
import com.coze.openapi.client.bots.PublishBotResp;
import com.coze.openapi.client.bots.RetrieveBotReq;
import com.coze.openapi.client.bots.RetrieveBotResp;
import com.coze.openapi.client.bots.RetrieveBotV2Req;
import com.coze.openapi.client.bots.RetrieveBotV2Resp;
import com.coze.openapi.client.bots.UpdateBotReq;
import com.coze.openapi.client.bots.UpdateBotResp;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.bots.model.BotSimpleInfo;
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

  public PageResp<SimpleBot> list(ListBotReq req) {
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



  private PageFetcher<SimpleBot> getSimpleBotPageFetcher(ListBotReq req) {
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

  public PageResp<BotSimpleInfo> list(ListBotV2Req req) {
    if (req == null) {
      throw new IllegalArgumentException("req is required");
    }

    Integer pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
    Integer pageSize = req.getPageSize() == null ? 20 : req.getPageSize();
    PageFetcher<BotSimpleInfo> pageFetcher = getSimpleBotPageFetcher(req);

    // 创建分页器
    PageNumBasedPaginator<BotSimpleInfo> paginator = new PageNumBasedPaginator<>(pageFetcher, pageSize);

    // 获取当前页数据
    PageRequest initialRequest = PageRequest.builder().pageNum(pageNum).pageSize(pageSize).build();

    PageResponse<BotSimpleInfo> firstPage = pageFetcher.fetch(initialRequest);

    return PageResp.<BotSimpleInfo>builder().total(firstPage.getTotal()).items(firstPage.getData()).iterator(paginator)
        .logID(firstPage.getLogID()).hasMore(firstPage.isHasMore()).build();
  }

  private PageFetcher<BotSimpleInfo> getSimpleBotPageFetcher(ListBotV2Req req) {
    String workspaceID = req.getWorkspaceID();
    String publishStatus = req.getPublishStatus();
    String connectorID = req.getConnectorID();
    // 创建分页获取器
    PageFetcher<BotSimpleInfo> pageFetcher = request -> {
      BaseResponse<ListBotV2Resp> resp = Utils
          .execute(api.list(workspaceID, publishStatus, connectorID, request.getPageNum(), request.getPageSize(), req));
      return PageResponse.<BotSimpleInfo>builder()
          .hasMore(resp.getData().getItems().size() == request.getPageSize())
          .data(resp.getData().getItems())
          .pageNum(request.getPageNum())
          .pageSize(request.getPageSize())
          .total(resp.getData().getTotal())
          .logID(resp.getLogID())
          .build();
    };
    return pageFetcher;
  }

  public RetrieveBotResp retrieve(RetrieveBotReq req) {
    BaseResponse<Bot> resp = Utils.execute(api.retrieve(req.getBotID(), req));
    return RetrieveBotResp.builder().bot(resp.getData()).logID(resp.getLogID()).build();
  }

  public RetrieveBotV2Resp retrieve(RetrieveBotV2Req req) {
    BaseResponse<Bot> resp = Utils.execute(api.retrieve(req.getBotID(), req.getIsPublished(), req));
    return RetrieveBotV2Resp.builder().bot(resp.getData()).logID(resp.getLogID()).build();
  }

  public CreateBotResp create(CreateBotReq req) {
    return Utils.execute(api.create(req, req)).getData();
  }

  public UpdateBotResp update(UpdateBotReq req) {
    String logID = Utils.execute(api.update(req, req)).getLogID();
    return UpdateBotResp.builder().logID(logID).build();
  }

  public PublishBotResp publish(PublishBotReq req) {
    return Utils.execute(api.publish(req, req)).getData();
  }
}
