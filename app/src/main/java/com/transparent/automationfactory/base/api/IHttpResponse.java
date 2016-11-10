package com.transparent.automationfactory.base.api;

public interface IHttpResponse<T> {

    void onSuccess(T result);

    void onFailure(String errorMsg);
}
