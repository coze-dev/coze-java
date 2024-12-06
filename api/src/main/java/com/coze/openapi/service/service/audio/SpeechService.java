package com.coze.openapi.service.service.audio;

import com.coze.openapi.api.AudioSpeechAPI;
import com.coze.openapi.client.audio.speech.CreateSpeechReq;
import com.coze.openapi.client.audio.speech.CreateSpeechResp;
import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.service.utils.Utils;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class SpeechService {
    private final AudioSpeechAPI api;

    public SpeechService(AudioSpeechAPI api) {
        this.api = api;
    }

    public CreateSpeechResp create(CreateSpeechReq req) {
        try{
            Response<ResponseBody> response = api.create(req, req).execute();
            if (!response.isSuccessful()) {
                throw new HttpException(response);
            }
            CreateSpeechResp resp = new CreateSpeechResp(response.body());
            resp.setLogID(Utils.getLogID(response));
            return resp;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
