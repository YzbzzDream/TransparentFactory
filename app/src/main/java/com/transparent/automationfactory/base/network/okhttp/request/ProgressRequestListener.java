package com.transparent.automationfactory.base.network.okhttp.request;

public interface ProgressRequestListener {

    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
