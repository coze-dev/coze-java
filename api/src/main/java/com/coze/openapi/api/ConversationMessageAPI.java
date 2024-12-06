package com.coze.openapi.api;


import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.CreateMessageReq;
import com.coze.openapi.client.connversations.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.ListMessageResp;
import com.coze.openapi.client.connversations.message.UpdateMessageReq;
import com.coze.openapi.client.connversations.message.UpdateMessageResp;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.common.BaseReq;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Tag;

public interface ConversationMessageAPI {
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/create")
    Single<Response<BaseResponse<Message>>> create(@Query("conversation_id")String conversationID, @Body CreateMessageReq req, @Tag BaseReq baseReq);


    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/list")
    Single<Response<ListMessageResp>> list(@Query("conversation_id") String conversationID, @Body ListMessageReq req, @Tag BaseReq baseReq);
        
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/retrieve")
    Single<Response<BaseResponse<Message>>> retrieve(@Query("conversation_id")String conversationID, @Query("message_id")String messageID, @Tag BaseReq baseReq);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/modify")
    Single<Response<UpdateMessageResp>> update(@Query("conversation_id")String conversationID, @Query("message_id")String messageID, @Body UpdateMessageReq req, @Tag BaseReq baseReq);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/delete")
    Single<Response<BaseResponse<Message>>> delete(@Query("conversation_id")String conversationID, @Query("message_id")String messageID, @Tag BaseReq baseReq);
}
