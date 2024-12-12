package com.coze.openapi.api;

import java.util.List;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.model.Message;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChatMessageAPI {

     @GET("/v3/chat/message/list")
    Single<BaseResponse<List<Message>>> listMessage(@Query("conversation_id") String conversationID, @Query("chat_id") String chatID);
} 
