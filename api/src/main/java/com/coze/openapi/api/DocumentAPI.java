package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.knowledge.document.CreateDocumentReq;
import com.coze.openapi.client.knowledge.document.CreateDocumentResp;
import com.coze.openapi.client.knowledge.document.DeleteDocumentReq;
import com.coze.openapi.client.knowledge.document.ListDocumentReq;
import com.coze.openapi.client.knowledge.document.ListDocumentResp;
import com.coze.openapi.client.knowledge.document.ModifyDocumentReq;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DocumentAPI {

    @POST("/open_api/knowledge/document/create")
    @Headers({"Content-Type: application/json","Agw-Js-Conv: str"})
    Single<CreateDocumentResp> CreateDocument(@Body CreateDocumentReq req);

    @POST("/open_api/knowledge/document/update")
    @Headers({"Content-Type: application/json","Agw-Js-Conv: str"})
    Single<BaseResponse<Void>> UpdateDocument(@Body ModifyDocumentReq req);

    @POST("/open_api/knowledge/document/delete")
    @Headers({"Content-Type: application/json","Agw-Js-Conv: str"})
    Single<BaseResponse<Void>> DeleteDocument(@Body DeleteDocumentReq req);

    @POST("/open_api/knowledge/document/list")
    @Headers({"Content-Type: application/json","Agw-Js-Conv: str"})
    Single<ListDocumentResp> ListDocument(@Body ListDocumentReq req);
}
