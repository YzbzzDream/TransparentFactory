package com.transparent.automationfactory.base.network.okhttp.request;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.transparent.automationfactory.base.network.okhttp.OKHttpManager;
import com.transparent.automationfactory.base.network.okhttp.response.ResponseHandlerInterface;
import com.transparent.automationfactory.base.util.CollectionUtil;

import java.io.IOException;
import java.util.Map;

public abstract class OKHttpRequest {

    private final String url;
    private final Object tag;
    private final Map<String, String> headers;
    private final Map<String, String> params;

    protected OKHttpManager okHttpManager;

    protected OKHttpRequest(Builder builder) {
        this.url = builder.url;
        this.tag = builder.tag;
        this.headers = builder.headers;
        this.params = builder.params;
        okHttpManager = OKHttpManager.getInstance();
    }

    public abstract static class Builder<T extends Builder> {

        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;

        public T url(String url) {
            this.url = url == null ? "" : url;
            return (T) this;
        }

        public T tag(Object tag) {
            this.tag = tag;
            return (T) this;
        }

        public T addHeader(String name, String value) {
            if (headers == null) {
                headers = CollectionUtil.linkedHashMap();
            }
            headers.put(name, value);
            return (T) this;
        }

        public T headers(Map<String, String> headers) {
            this.headers = headers;
            return (T) this;
        }

        public T params(Map<String, String> params) {

            this.params = params;
            return (T) this;
        }

        public abstract <T extends OKHttpRequest> T build();
    }

    public String getUrl() {
        return url;
    }

    public Object getTag() {
        return tag;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void cancel(Object tag) {
        okHttpManager.cancel(tag);
    }

    public void request(ResponseHandlerInterface responseHandlerInterface) throws IOException {
        try {
            Request request = buildRequest();
            Call call = okHttpManager.getOkHttpClient().newCall(request);
            okHttpManager.sendRequest(call, responseHandlerInterface);
        } catch (Exception e) {
            responseHandlerInterface.onFailure(null, e);
        }
    }

    public Response request() throws IOException {
        try {
            Request request = buildRequest();
            Call call = okHttpManager.getOkHttpClient().newCall(request);
            return okHttpManager.sendRequest(call);
        } catch (Exception e) {
            return null;
        }
    }


    protected Request.Builder getBuilder() {
        Request.Builder builder = new Request.Builder();
        Object tag = getTag();

        if (tag != null) {
            builder.tag(tag);
        }

//		builder.header("","");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> param : headers.entrySet()) {
                builder.addHeader(param.getKey(), param.getValue());
            }
        }
        return builder;
    }

    public abstract Request buildRequest();

    public static String appendUrl(String url, Map<String, String> urlParams) {
        if (null == url) {
            return "";
        }
        if (urlParams == null || urlParams.size() <= 0) {
            return url;
        }
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (null == httpUrl) {
            return url;
        }
        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return urlBuilder.build().toString();
    }
}
