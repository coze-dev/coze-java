package com.coze.openapi.service.service;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

// debug 的时候用的，后面记得删
@Deprecated
public class MyInterceptor implements okhttp3.Interceptor {

    MyInterceptor() {}

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();

        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            String bodyStr = buffer.readUtf8();
            System.out.println(bodyStr);
        }
        Response response = chain.proceed(request);
        Headers h = response.headers();
        System.out.println(h.get("x-tt-logid"));
    //     ResponseBody responseBody = response.body();
    //     if (responseBody!= null) {
    //        String bodyStr = responseBody.string();
    //        System.out.println(bodyStr);
    //    }
        return response;
    }
}
