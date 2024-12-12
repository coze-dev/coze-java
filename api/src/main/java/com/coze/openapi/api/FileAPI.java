package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.files.model.FileInfo;

import io.reactivex.Single;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FileAPI {
    @Multipart
    @POST("/v1/files/upload")
    Single<BaseResponse<FileInfo>> uploadFile(@Part MultipartBody.Part file);

    
    @GET("/v1/files/retrieve")
    Single<BaseResponse<FileInfo>> retrieveFile(@Query("file_id") String fileID);
}
