package com.coze.openapi.api;


import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.CreateMessageReq;
import com.coze.openapi.client.connversations.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.GetMessageListResp;
import com.coze.openapi.client.connversations.message.UpdateMessageReq;
import com.coze.openapi.client.connversations.message.UpdateMessageResp;
import com.coze.openapi.client.connversations.message.model.Message;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ConversationMessageAPI {
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/create")
    Single<BaseResponse<Message>> create(@Query("conversation_id")String conversationID, @Body CreateMessageReq req);


    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/list")
    Single<GetMessageListResp> list(@Query("conversation_id") String conversationID, @Body ListMessageReq req);
        
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/retrieve")
    Single<BaseResponse<Message>> retrieve(@Query("conversation_id")String conversationID, @Query("message_id")String messageID);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/modify")
    Single<UpdateMessageResp> update(@Query("conversation_id")String conversationID, @Query("message_id")String messageID, @Body UpdateMessageReq req);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/message/delete")
    Single<BaseResponse<Message>> delete(@Query("conversation_id")String conversationID, @Query("message_id")String messageID);
}
