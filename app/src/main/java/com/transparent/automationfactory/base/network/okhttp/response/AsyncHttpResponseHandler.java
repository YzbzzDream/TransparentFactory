package com.transparent.automationfactory.base.network.okhttp.response;

import com.squareup.okhttp.Response;

public abstract class AsyncHttpResponseHandler implements ResponseHandlerInterface {

    @Override
    public final void onResponse(Response response) {
        if (response.isSuccessful()) {
            onResponsed(response);
        } else {
            onFailure(response.request(), null);
        }

    }

    public abstract void onResponsed(Response response);

    public abstract void onError(Throwable e);

}
