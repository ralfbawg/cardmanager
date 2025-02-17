package com.ralf.cardmanager.spider.task;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.IOException;
import java.util.Map;

public abstract class BaseOperation<T extends Object> {

    private String defaultUserAgent = "";

    private String defaultUrl = "";

    public abstract T execute() throws IOException;

    public abstract void persistent(String rsp);


    public String url = "";

    protected HttpRequestBase requestBase;

    protected Map<String, String> defaultHeader;

    protected HttpRequestBase getClient(String url) {
        return getHttpClient(HttpConstant.Method.GET, url);
    }

    protected HttpRequestBase postClient(String url) {
        return getHttpClient(HttpConstant.Method.POST, url);
    }

    protected HttpRequestBase getHttpClient(String type, String url) {
        switch (type.toUpperCase()) {
            case "GET":
            default:
                return new HttpGet(url);
            case "POST":
                return new HttpPost(url);
        }
    }

    protected BaseOperation setBody(String body) {
        return setBody(body, ContentType.APPLICATION_JSON);
    }

    protected BaseOperation setBody(String body, ContentType type) {
        ((HttpPost) requestBase).setEntity(new StringEntity(body, type));
        return this;
    }

    protected BaseOperation setParam(Map params) {
        return this;
    }

    protected BaseOperation setHeader(Map<String, String> headers) {
        if (headers != null) {
            headers.forEach((key, value) -> defaultHeader.merge(key, value, (v1, v2) -> v2));
        }
        defaultHeader.forEach((k, v) -> {
            requestBase.setHeader(k, v);
        });
        return this;
    }

    public abstract void packageRequest();

    public abstract void packageParams();

    public abstract String getUrl();

}
