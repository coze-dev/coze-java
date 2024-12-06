package com.coze.openapi.api;

import java.util.List;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.model.Message;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Tag;

public interface ChatMessageAPI {

    @GET("/v3/chat/message/list")
    Single<Response<BaseResponse<List<Message>>>> list(@Query("conversation_id") String conversationID, @Query("chat_id") String chatID, @Tag BaseReq baseReq);
} 
