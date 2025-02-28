package com.coze.openapi.service.service.websocket.common;

import com.coze.openapi.client.websocket.event.downstream.ErrorEvent;

public abstract class BaseCallbackHandler<T>{

    public BaseCallbackHandler() {}

    // websocket closing
    public void onClosing(T client, int code, String reason) {}

    // websocket closed
    public void onClosed(T client, int code, String reason) {}

    // coze api exception
    public void onError(T client, ErrorEvent event) {}

    // websocket connection failed
    public void onFailure(T client, Throwable t) {}

    // sdk exception
    public void onClientException(T client, Throwable t) {}

}
