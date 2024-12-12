/* (C)2024 */
package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.CreateMessageReq;
import com.coze.openapi.client.connversations.message.ListMessageReq;
import com.coze.openapi.client.connversations.message.ListMessageResp;
import com.coze.openapi.client.connversations.message.UpdateMessageReq;
import com.coze.openapi.client.connversations.message.UpdateMessageResp;
import com.coze.openapi.client.connversations.message.model.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Tag;

public interface ConversationMessageAPI {
  @Headers({"Content-Type: application/json"})
  @POST("/v1/conversation/message/create")
  Call<BaseResponse<Message>> create(
      @Query("conversation_id") String conversationID,
      @Body CreateMessageReq req,
      @Tag BaseReq baseReq);

  @Headers({"Content-Type: application/json"})
  @POST("/v1/conversation/message/list")
  Call<ListMessageResp> list(
      @Query("conversation_id") String conversationID,
      @Body ListMessageReq req,
      @Tag BaseReq baseReq);

  @Headers({"Content-Type: application/json"})
  @POST("/v1/conversation/message/retrieve")
  Call<BaseResponse<Message>> retrieve(
      @Query("conversation_id") String conversationID,
      @Query("message_id") String messageID,
      @Tag BaseReq baseReq);

  @Headers({"Content-Type: application/json"})
  @POST("/v1/conversation/message/modify")
  Call<UpdateMessageResp> update(
      @Query("conversation_id") String conversationID,
      @Query("message_id") String messageID,
      @Body UpdateMessageReq req,
      @Tag BaseReq baseReq);

  @Headers({"Content-Type: application/json"})
  @POST("/v1/conversation/message/delete")
  Call<BaseResponse<Message>> delete(
      @Query("conversation_id") String conversationID,
      @Query("message_id") String messageID,
      @Tag BaseReq baseReq);
}
