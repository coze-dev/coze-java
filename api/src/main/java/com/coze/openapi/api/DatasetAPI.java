package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.dataset.CreateDatasetReq;
import com.coze.openapi.client.dataset.CreateDatasetResp;
import com.coze.openapi.client.dataset.DeleteDatasetResp;
import com.coze.openapi.client.dataset.ListDatasetResp;
import com.coze.openapi.client.dataset.ProcessDatasetReq;
import com.coze.openapi.client.dataset.ProcessDatasetResp;
import com.coze.openapi.client.dataset.UpdateDatasetReq;
import com.coze.openapi.client.dataset.UpdateDatasetResp;

import retrofit2.Call;
import retrofit2.http.*;

public interface DatasetAPI {
  @POST("v1/datasets")
  Call<BaseResponse<CreateDatasetResp>> create(@Body CreateDatasetReq req, @Tag BaseReq baseReq);

  @GET("v1/datasets")
  Call<BaseResponse<ListDatasetResp>> list(
      @Query("space_id") String spaceID,
      @Query("name") String name,
      @Query("format_type") Integer formatType,
      @Query("page_size") Integer pageSize,
      @Query("page_num") Integer pageNum,
      @Tag BaseReq baseReq);

  @PUT("v1/datasets/{dataset_id}")
  Call<BaseResponse<UpdateDatasetResp>> update(
      @Path("dataset_id") String datasetID, @Body UpdateDatasetReq req, @Tag BaseReq baseReq);

  @DELETE("v1/datasets/{dataset_id}")
  Call<BaseResponse<DeleteDatasetResp>> delete(
      @Path("dataset_id") String datasetID, @Tag BaseReq baseReq);

  @POST("v1/datasets/{dataset_id}/process")
  Call<BaseResponse<ProcessDatasetResp>> process(
      @Path("dataset_id") String datasetID, @Body ProcessDatasetReq req, @Tag BaseReq baseReq);
}
