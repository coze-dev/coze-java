package com.coze.openapi.service.service;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

import com.coze.openapi.service.auth.Auth;

public class AuthenticationInterceptor implements Interceptor{
    private final Auth auth;

    AuthenticationInterceptor(Auth auth) {
        Objects.requireNonNull(auth, "OpenAI token required");
        this.auth = auth;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .header("Authorization", auth.tokenType() + " " + auth.token())
                .build();
        return chain.proceed(request);
    }
}
