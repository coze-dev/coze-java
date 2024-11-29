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
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Body;

public interface BotAPI {
    @GET("/v1/space/published_bots_list")
    Single<BaseResponse<ListBotResp>> list(@Query("space_id") String spaceID, @Query("page_index") Integer page, @Query("page_size") Integer pageSize);

    @GET("/v1/bot/get_online_info")
    Single<BaseResponse<Bot>> retrieve(@Query("bot_id") String botID);

    @POST("/v1/bot/create")
    Single<BaseResponse<CreateBotResp>> create(@Body CreateBotReq req);

    @POST("/v1/bot/update") 
    Single<BaseResponse<Void>> update(@Body UpdateBotReq req);

    @POST("/v1/bot/publish")
    Single<BaseResponse<PublishBotResp>> publish(@Body PublishBotReq req);
} 
