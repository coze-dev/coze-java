package com.coze.openapi.api;

import java.util.List;

import com.coze.openapi.client.commerce.benefit.bill.CreateBillDownloadTaskReq;
import com.coze.openapi.client.commerce.benefit.bill.ListBillDownloadTaskResp;
import com.coze.openapi.client.commerce.benefit.bill.model.BillTaskInfo;
import com.coze.openapi.client.commerce.benefit.limitation.*;
import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface CommerceBenefitBillAPI {
  @Headers({"Content-Type: application/json", "Agw-Js-Conv: str"})
  @POST("/v1/commerce/benefit/bill_tasks")
  Call<BaseResponse<BillTaskInfo>> create(
      @Body CreateBillDownloadTaskReq req, @Tag BaseReq baseReq);

  @Headers({"Content-Type: application/json", "Agw-Js-Conv: str"})
  @GET("/v1/commerce/benefit/bill_tasks")
  Call<BaseResponse<ListBillDownloadTaskResp>> list(
      @Query("task_ids") List<String> taskIDs,
      @Query("page_num") Integer pageNum,
      @Query("page_size") Integer pageSize,
      @Tag BaseReq baseReq);
}
