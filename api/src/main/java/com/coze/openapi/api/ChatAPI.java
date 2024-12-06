package com.coze.openapi.api;

import com.coze.openapi.client.chat.CancelChatReq;
import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.SubmitToolOutputsReq;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Tag;

public interface ChatAPI {

    @POST("/v3/chat")
    Single<Response<BaseResponse<Chat>>> chat(@Query("conversation_id") String conversationID, @Body CreateChatReq req, @Tag BaseReq baseReq);

    @POST("/v3/chat")
    @Streaming
    Call<ResponseBody> stream(@Query("conversation_id") String conversationID, @Body CreateChatReq req, @Tag BaseReq baseReq);

    @GET("/v3/chat/retrieve")
    Single<Response<BaseResponse<Chat>>> retrieve(@Query("conversation_id") String conversationID, @Query("chat_id") String chatID, @Tag BaseReq baseReq);
    
    @POST("/v3/chat/cancel")
    Single<Response<BaseResponse<Chat>>> cancel(@Body CancelChatReq req, @Tag BaseReq baseReq);

    @POST("/v3/chat/submit_tool_outputs")
    @Streaming
    Call<ResponseBody> streamSubmitToolOutputs(@Query("conversation_id") String conversationID, @Query("chat_id") String chatID, @Body SubmitToolOutputsReq req, @Tag BaseReq baseReq);

    @POST("/v3/chat/submit_tool_outputs")
    Single<Response<BaseResponse<Chat>>> submitToolOutputs(@Query("conversation_id") String conversationID, @Query("chat_id") String chatID, @Body SubmitToolOutputsReq req, @Tag BaseReq baseReq);


}
