package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.ClearConversationResp;
import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.CreateConversationResp;
import com.coze.openapi.client.connversations.GetConversationResp;
import com.coze.openapi.client.connversations.ListConversationResp;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConversationAPI {
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/create")
    Single<BaseResponse<CreateConversationResp>> CreateConversation(@Body CreateConversationReq req);
    
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/retrieve")
    Single<BaseResponse<GetConversationResp>> RetrieveConversation(@Query("conversation_id")String id);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversations")
    Single<BaseResponse<ListConversationResp>> ListConversation(@Query("bot_id") String botID, @Query("page_num") Integer pageNum, @Query("page_size") Integer pageSize);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversations/{conversation_id}/clear")
    Single<BaseResponse<ClearConversationResp>> ClearConversation(@Path("conversation_id") String conversationID);
}
