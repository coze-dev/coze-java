package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.ClearConversationResult;
import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.ListConversationResult;

import com.coze.openapi.client.connversations.model.Conversation;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConversationAPI {
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/create")
    Single<BaseResponse<Conversation>> create(@Body CreateConversationReq req);
    
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/retrieve")
    Single<BaseResponse<Conversation>> retrieve(@Query("conversation_id")String id);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversations")
    Single<BaseResponse<ListConversationResult>> list(@Query("bot_id") String botID, @Query("page_num") Integer pageNum, @Query("page_size") Integer pageSize);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversations/{conversation_id}/clear")
    Single<BaseResponse<ClearConversationResult>> clear(@Path("conversation_id") String conversationID);
}
