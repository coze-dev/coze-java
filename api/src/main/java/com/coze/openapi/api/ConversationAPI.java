package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.ClearConversationResp;
import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.ListConversationResp;

import com.coze.openapi.client.connversations.model.Conversation;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.coze.openapi.client.common.BaseReq;
import retrofit2.http.Tag;

public interface ConversationAPI {
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/create")
    Single<Response<BaseResponse<Conversation>>> create(@Body CreateConversationReq req, @Tag BaseReq baseReq);
    
    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversation/retrieve")
    Single<Response<BaseResponse<Conversation>>> retrieve(@Query("conversation_id")String id, @Tag BaseReq baseReq);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversations")
    Single<Response<BaseResponse<ListConversationResp>>> list(@Query("bot_id") String botID, @Query("page_num") Integer pageNum, @Query("page_size") Integer pageSize, @Tag BaseReq baseReq);

    @Headers({"Content-Type: application/json"})
    @POST("/v1/conversations/{conversation_id}/clear")
    Single<Response<BaseResponse<ClearConversationResp>>> clear(@Path("conversation_id") String conversationID, @Tag BaseReq baseReq);
}
