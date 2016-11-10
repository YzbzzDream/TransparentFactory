package com.transparent.automationfactory.base.api;


public interface IAsyncHttpResponseHandler {

    void onSuccess(String result);

    void onFailure(int code, Object obj, Throwable e);
}
