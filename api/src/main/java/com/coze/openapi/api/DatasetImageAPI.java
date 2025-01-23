package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.dataset.image.ListImageResp;
import com.coze.openapi.client.dataset.image.UpdateImageReq;
import com.coze.openapi.client.dataset.image.UpdateImageResp;

import retrofit2.Call;
import retrofit2.http.*;

public interface DatasetImageAPI {
  @PUT("v1/datasets/{dataset_id}/images/{document_id}")
  Call<BaseResponse<UpdateImageResp>> update(
      @Path("dataset_id") String datasetID,
      @Path("document_id") String documentID,
      @Body UpdateImageReq req,
      @Tag BaseReq baseReq);

  @GET("v1/datasets/{dataset_id}/images")
  Call<BaseResponse<ListImageResp>> list(
      @Path("dataset_id") String datasetID,
      @Query("keyword") String keyword,
      @Query("has_caption") Boolean hasCaption,
      @Query("page_num") Integer pageNum,
      @Query("page_size") Integer pageSize,
      @Tag BaseReq baseReq);
}
