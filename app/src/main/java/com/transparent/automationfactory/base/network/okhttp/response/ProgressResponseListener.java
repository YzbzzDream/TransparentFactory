package com.transparent.automationfactory.base.network.okhttp.response;

public interface ProgressResponseListener {

    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
