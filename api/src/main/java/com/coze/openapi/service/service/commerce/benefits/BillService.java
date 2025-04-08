package com.coze.openapi.service.service.commerce.benefits;

import com.coze.openapi.api.CommerceBenefitBillAPI;
import com.coze.openapi.client.commerce.benefit.bill.CreateBillDownloadTaskReq;
import com.coze.openapi.client.commerce.benefit.bill.CreateBillDownloadTaskResp;
import com.coze.openapi.client.commerce.benefit.bill.ListBillDownloadTaskReq;
import com.coze.openapi.client.commerce.benefit.bill.ListBillDownloadTaskResp;
import com.coze.openapi.client.commerce.benefit.bill.model.BillTaskInfo;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.*;
import com.coze.openapi.service.utils.Utils;

public class BillService {
  private final CommerceBenefitBillAPI api;

  public BillService(CommerceBenefitBillAPI billAPI) {
    this.api = billAPI;
  }

  public CreateBillDownloadTaskResp create(CreateBillDownloadTaskReq req) {
    BaseResponse<BillTaskInfo> resp = Utils.execute(api.create(req, req));
    return CreateBillDownloadTaskResp.builder()
        .billTaskInfo(resp.getData())
        .logID(resp.getLogID())
        .build();
  }

  public PageResp<BillTaskInfo> list(ListBillDownloadTaskReq req) {

    Integer pageSize = req.getPageSize();
    if (pageSize == null) {
      pageSize = 20;
    }
    PageFetcher<BillTaskInfo> pageFetcher = getBillTaskPageFetcher(req);

    // 创建基于 token 的分页器
    PageNumBasedPaginator<BillTaskInfo> paginator =
        new PageNumBasedPaginator<>(pageFetcher, pageSize);

    PageRequest initialRequest =
        PageRequest.builder().pageNum(req.getPageNum()).pageSize(pageSize).build();

    PageResponse<BillTaskInfo> firstPage = pageFetcher.fetch(initialRequest);

    return PageResp.<BillTaskInfo>builder()
        .items(firstPage.getData())
        .iterator(paginator)
        .logID(firstPage.getLogID())
        .hasMore(firstPage.isHasMore())
        .build();
  }

  private PageFetcher<BillTaskInfo> getBillTaskPageFetcher(ListBillDownloadTaskReq req) {
    // 创建分页获取器
    PageFetcher<BillTaskInfo> pageFetcher =
        request -> {
          ListBillDownloadTaskResp resp =
              Utils.execute(
                      api.list(req.getTaskIds(), request.getPageNum(), request.getPageSize(), req))
                  .getData();
          return PageResponse.<BillTaskInfo>builder()
              .hasMore(resp.getTaskInfos().size() == request.getPageSize())
              .data(resp.getTaskInfos())
              .pageNum(request.getPageNum())
              .total(resp.getTotal())
              .pageSize(request.getPageSize())
              .logID(resp.getLogID())
              .build();
        };
    return pageFetcher;
  }
}
