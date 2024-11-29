package com.coze.openapi.api;

import com.coze.openapi.client.chat.CancelChatReq;
import com.coze.openapi.client.chat.ChatReq;
import com.coze.openapi.client.chat.SubmitToolOutputsReq;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.common.BaseResponse;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ChatAPI {

    @POST("/v3/chat")
    Single<BaseResponse<Chat>> chat(@Query("conversation_id") String conversationID, @Body ChatReq req);

    @POST("/v3/chat")
    @Streaming
    Call<ResponseBody> stream(@Query("conversation_id") String conversationID, @Body ChatReq req);

    @GET("/v3/chat/retrieve")
    Single<BaseResponse<Chat>> retrieve(@Query("conversation_id") String conversationID, @Query("chat_id") String chatID);
    
    @POST("/v3/chat/cancel")
    Single<BaseResponse<Chat>> cancel(@Body CancelChatReq req);

    @POST("/v3/chat/submit_tool_outputs")
    @Streaming
    Call<ResponseBody> streamSubmitToolOutputs(@Query("conversation_id") String conversationID, @Query("chat_id") String chatID, @Body SubmitToolOutputsReq req);

    @POST("/v3/chat/submit_tool_outputs")
    Single<BaseResponse<Chat>> submitToolOutputs(@Query("conversation_id") String conversationID, @Query("chat_id") String chatID, @Body SubmitToolOutputsReq req);


}
