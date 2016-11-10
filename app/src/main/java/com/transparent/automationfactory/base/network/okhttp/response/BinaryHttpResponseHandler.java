package com.transparent.automationfactory.base.network.okhttp.response;

import android.util.Pair;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public abstract class BinaryHttpResponseHandler implements ResponseHandlerInterface {

    protected static final int BUFFER_SIZE = 4096;

    @Override
    public final void onResponse(Response response) {
        if (response.isSuccessful()) {
            setData(response);
        } else {
            Observable.just(response).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
                @Override
                public void call(Response response) {
                    onUIFailure(response.code(), response.request(), null);
                }
            });
        }
    }

    public final void onFailure(Request request, Exception e) {
        Observable.just(Pair.create(request, e)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Pair<Request, Exception>>() {

            @Override
            public void call(Pair<Request, Exception> requestExceptionPair) {
                onUIFailure(0, requestExceptionPair.first, requestExceptionPair.second);
            }
        });
    }

    public abstract void onUIFailure(int code, Request request, Exception e);

    public abstract void onUIResponse(int statusCode, byte[] bytes);

    public abstract void onUIError(Throwable e);

    public void setData(final Response response) {
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(response.body().byteStream());
            outputStream = new ByteArrayOutputStream();
            byte[] temp = new byte[BUFFER_SIZE];
            int size = 0;
            while ((size = bufferedInputStream.read(temp)) != -1) {
                outputStream.write(temp, 0, size);
            }
            byte[] content = outputStream.toByteArray();
            Observable.just(content).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<byte[]>() {
                @Override
                public void call(byte[] bytes) {
                    onUIResponse(response.code(), bytes);
                }
            });
        } catch (Exception e) {
            Observable.just(e).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Exception>() {
                @Override
                public void call(Exception e) {
                    onUIError(e);
                }
            });
        } finally {
            closeStream(bufferedInputStream);
            closeStream(outputStream);
        }
    }

    private void closeStream(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
