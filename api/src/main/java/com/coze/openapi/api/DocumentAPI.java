package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.dataset.document.CreateDocumentReq;
import com.coze.openapi.client.dataset.document.CreateDocumentResp;
import com.coze.openapi.client.dataset.document.DeleteDocumentReq;
import com.coze.openapi.client.dataset.document.ListDocumentReq;
import com.coze.openapi.client.dataset.document.ListDocumentResp;
import com.coze.openapi.client.dataset.document.UpdateDocumentReq;
import com.coze.openapi.client.common.BaseReq;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Tag;

public interface DocumentAPI {

    @POST("/open_api/knowledge/document/create")
    @Headers({"Content-Type: application/json","Agw-Js-Conv: str"})
    Call<CreateDocumentResp> create(@Body CreateDocumentReq req, @Tag BaseReq baseReq);

    @POST("/open_api/knowledge/document/update")
    @Headers({"Content-Type: application/json","Agw-Js-Conv: str"})
    Call<BaseResponse<Void>> update(@Body UpdateDocumentReq req, @Tag BaseReq baseReq);

    @POST("/open_api/knowledge/document/delete")
    @Headers({"Content-Type: application/json","Agw-Js-Conv: str"})
    Call<BaseResponse<Void>> delete(@Body DeleteDocumentReq req, @Tag BaseReq baseReq);

    @POST("/open_api/knowledge/document/list")
    @Headers({"Content-Type: application/json","Agw-Js-Conv: str"})
    Call<ListDocumentResp> list(@Body ListDocumentReq req, @Tag BaseReq baseReq);
}
