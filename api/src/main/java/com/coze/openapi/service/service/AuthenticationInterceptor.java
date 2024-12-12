/* (C)2024 */
package com.coze.openapi.service.service;

import java.io.IOException;
import java.util.Objects;

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
    Request request =
        chain
            .request()
            .newBuilder()
            .header("Authorization", auth.tokenType() + " " + auth.token())
            .build();
    return chain.proceed(request);
  }
}
