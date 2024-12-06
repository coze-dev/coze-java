package com.coze.openapi.api;

import com.coze.openapi.client.bots.CreateBotReq;
import com.coze.openapi.client.bots.CreateBotResp;
import com.coze.openapi.client.bots.ListBotResp;
import com.coze.openapi.client.bots.UpdateBotReq;
import com.coze.openapi.client.bots.PublishBotReq;
import com.coze.openapi.client.bots.PublishBotResp;
import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.common.BaseResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Body;
import retrofit2.Response;
import retrofit2.http.Tag;

import com.coze.openapi.client.common.BaseReq;
public interface BotAPI {
    @GET("/v1/space/published_bots_list")
    Call<BaseResponse<ListBotResp>> list(@Query("space_id") String spaceID, @Query("page_index") Integer page, @Query("page_size") Integer pageSize, @Tag BaseReq baseReq);

    @GET("/v1/bot/get_online_info")
    Call<BaseResponse<Bot>> retrieve(@Query("bot_id") String botID, @Tag BaseReq baseReq);

    @POST("/v1/bot/create")
    Call<BaseResponse<CreateBotResp>> create(@Body CreateBotReq req, @Tag BaseReq baseReq);

    @POST("/v1/bot/update") 
    Call<BaseResponse<Void>> update(@Body UpdateBotReq req, @Tag BaseReq baseReq);

    @POST("/v1/bot/publish")
    Call<BaseResponse<PublishBotResp>> publish(@Body PublishBotReq req, @Tag BaseReq baseReq);
} 
