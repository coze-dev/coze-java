/* (C)2024 */
package com.coze.openapi.service.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.common.BaseReq;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TimeoutInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();

    // 从请求中获取 BaseReq 对象
    Object tag = request.tag(BaseReq.class);
    if (!(tag instanceof BaseReq)) {
      return chain.proceed(request);
    }

    BaseReq baseReq = (BaseReq) tag;
    Chain newChain = chain;

    // 只有当设置了超时时间时才修改
    if (baseReq.getConnectTimeout() != null) {
      newChain = newChain.withConnectTimeout(baseReq.getConnectTimeout(), TimeUnit.MILLISECONDS);
    }
    if (baseReq.getReadTimeout() != null) {
      newChain = newChain.withReadTimeout(baseReq.getReadTimeout(), TimeUnit.MILLISECONDS);
    }
    if (baseReq.getWriteTimeout() != null) {
      newChain = newChain.withWriteTimeout(baseReq.getWriteTimeout(), TimeUnit.MILLISECONDS);
    }

    return newChain.proceed(request);
  }
}
