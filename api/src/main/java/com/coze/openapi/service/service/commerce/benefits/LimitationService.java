package com.coze.openapi.service.service.commerce.benefits;

import com.coze.openapi.api.CommerceBenefitLimitationAPI;
import com.coze.openapi.client.commerce.benefit.limitation.*;
import com.coze.openapi.client.commerce.benefit.limitation.model.BenefitInfo;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.*;
import com.coze.openapi.service.utils.Utils;

public class LimitationService {
  private final CommerceBenefitLimitationAPI api;

  public LimitationService(CommerceBenefitLimitationAPI limitationAPI) {
    this.api = limitationAPI;
  }

  public CreateBenefitLimitationResp create(CreateBenefitLimitationReq req) {
    BaseResponse<CreateBenefitLimitationResp> resp = Utils.execute(api.create(req, req));
    CreateBenefitLimitationResp data = resp.getData();
    data.setLogID(resp.getLogID());
    return data;
  }

  public UpdateBenefitLimitationResp update(UpdateBenefitLimitationReq req) {
    String logID = Utils.execute(api.update(req.getBenefitID(), req, req)).getLogID();
    return UpdateBenefitLimitationResp.builder().logID(logID).build();
  }

  public PageResp<BenefitInfo> list(ListBenefitLimitationReq req) {

    Integer pageSize = req.getPageSize();
    if (pageSize == null) {
      pageSize = 20;
    }
    PageFetcher<BenefitInfo> pageFetcher = getLimitationPageFetcher(req);

    // 创建基于 token 的分页器
    TokenBasedPaginator<BenefitInfo> paginator = new TokenBasedPaginator<>(pageFetcher, pageSize);

    // 获取当前页数据
    PageRequest initialRequest =
        PageRequest.builder().pageSize(pageSize).pageToken(req.getPageToken()).build();

    PageResponse<BenefitInfo> currentPage = pageFetcher.fetch(initialRequest);
    paginator.setCurrentPage(currentPage);

    return PageResp.<BenefitInfo>builder()
        .items(currentPage.getData())
        .iterator(paginator)
        .lastID(currentPage.getLastID())
        .firstID(currentPage.getFirstID())
        .hasMore(currentPage.isHasMore())
        .build();
  }

  private PageFetcher<BenefitInfo> getLimitationPageFetcher(ListBenefitLimitationReq req) {
    // 创建分页获取器
    PageFetcher<BenefitInfo> pageFetcher =
        request -> {
          req.setPageToken(request.getPageToken());
          String entityType = null;
          String benefitType = null;
          String status = null;
          if (req.getEntityType() != null) {
            entityType = req.getEntityType().getValue();
          }
          if (req.getBenefitType() != null) {
            benefitType = req.getBenefitType().getValue();
          }
          if (req.getStatus() != null) {
            status = req.getStatus().getValue();
          }

          ListBenefitLimitationResp resp =
              Utils.execute(
                      api.list(
                          entityType,
                          request.getPageToken(),
                          req.getPageSize(),
                          req.getEntityID(),
                          benefitType,
                          status,
                          req))
                  .getData();
          return PageResponse.<BenefitInfo>builder()
              .hasMore(resp.isHasMore())
              .pageToken(resp.getPageToken())
              .data(resp.getBenefitInfos())
              .build();
        };
    return pageFetcher;
  }
}
