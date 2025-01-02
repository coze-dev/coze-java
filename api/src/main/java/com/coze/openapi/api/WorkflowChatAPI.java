package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.workflows.chat.WorkflowChatReq;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Tag;

public interface WorkflowChatAPI {
  @POST("/v1/workflows/chat")
  @Streaming
  Call<ResponseBody> stream(@Body WorkflowChatReq req, @Tag BaseReq baseReq);
}
