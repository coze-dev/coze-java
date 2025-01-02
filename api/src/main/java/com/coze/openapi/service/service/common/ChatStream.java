package com.coze.openapi.service.service.common;

import com.coze.openapi.client.chat.model.ChatEvent;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ChatStream {
    public static Flowable<ChatEvent> stream(Call<ResponseBody> apiCall) {
        return Flowable.create(
                emitter -> apiCall.enqueue(new ChatEventCallback(emitter)), BackpressureStrategy.BUFFER);
    }
}
