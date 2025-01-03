package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.files.model.FileInfo;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Tag;

public interface FileAPI {
  @Multipart
  @POST("/v1/files/upload")
  Call<BaseResponse<FileInfo>> upload(@Part MultipartBody.Part file, @Tag BaseReq baseReq);

  @GET("/v1/files/retrieve")
  Call<BaseResponse<FileInfo>> retrieve(@Query("file_id") String fileID, @Tag BaseReq baseReq);
}
