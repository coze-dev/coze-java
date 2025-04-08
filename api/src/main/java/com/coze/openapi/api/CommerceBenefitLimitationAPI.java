package com.coze.openapi.api;

import com.coze.openapi.client.commerce.benefit.limitation.CreateBenefitLimitationReq;
import com.coze.openapi.client.commerce.benefit.limitation.CreateBenefitLimitationResp;
import com.coze.openapi.client.commerce.benefit.limitation.ListBenefitLimitationResp;
import com.coze.openapi.client.commerce.benefit.limitation.UpdateBenefitLimitationReq;
import com.coze.openapi.client.commerce.benefit.limitation.UpdateBenefitLimitationResp;
import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Tag;

public interface CommerceBenefitLimitationAPI {
  @Headers({"Content-Type: application/json", "Agw-Js-Conv: str"})
  @POST("/v1/commerce/benefit/limitations")
  Call<BaseResponse<CreateBenefitLimitationResp>> create(
      @Body CreateBenefitLimitationReq req, @Tag BaseReq baseReq);

  @Headers({"Content-Type: application/json", "Agw-Js-Conv: str"})
  @PUT("/v1/commerce/benefit/limitations/{benefit_id}")
  Call<BaseResponse<UpdateBenefitLimitationResp>> update(
      @Path("benefit_id") String id, @Body UpdateBenefitLimitationReq req, @Tag BaseReq baseReq);

  @Headers({"Content-Type: application/json", "Agw-Js-Conv: str"})
  @GET("/v1/commerce/benefit/limitations")
  Call<BaseResponse<ListBenefitLimitationResp>> list(
      @Query("entity_type") String entityType,
      @Query("page_token") String pageToken,
      @Query("page_size") Integer pageSize,
      @Query("entity_id") String entityID,
      @Query("benefit_type") String benefitType,
      @Query("status") String status,
      @Tag BaseReq baseReq);
}
