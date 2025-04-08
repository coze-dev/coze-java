package com.coze.openapi.service.service;

import java.io.IOException;
import java.util.Objects;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.service.auth.Auth;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {
  private final Auth auth;

  AuthenticationInterceptor(Auth auth) {
    Objects.requireNonNull(auth, "Auth required");
    this.auth = auth;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    Object tag = request.tag(BaseReq.class);
    if (!(tag instanceof BaseReq)) {
      return chain.proceed(
          request
              .newBuilder()
              .header("Authorization", auth.tokenType() + " " + auth.token())
              .build());
    }

    BaseReq baseReq = (BaseReq) tag;
    if (baseReq.getCustomerToken() != null) {
      return chain.proceed(
          request
              .newBuilder()
              .header("Authorization", "Bearer " + baseReq.getCustomerToken())
              .build());
    }
    return chain.proceed(
        request
            .newBuilder()
            .header("Authorization", auth.tokenType() + " " + auth.token())
            .build());
  }
}
