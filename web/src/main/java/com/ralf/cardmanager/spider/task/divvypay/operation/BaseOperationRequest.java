package com.ralf.cardmanager.spider.task.divvypay.operation;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import us.codecraft.webmagic.utils.HttpConstant;

public abstract class BaseOperationRequest {
    public static String url = "";
    HttpRequestBase client;


    protected HttpRequestBase getClient(String url) {
        return getHttpCliet(HttpConstant.Method.GET, url);
    }

    protected HttpRequestBase postClient(String url) {
        return getHttpCliet(HttpConstant.Method.POST, url);
    }

    protected HttpRequestBase getHttpCliet(String type, String url) {
        switch (type.toUpperCase()) {
            case "GET":
            default:
                return new HttpGet(url);
            case "POST":
                return new HttpPost(url);
        }
    }

    protected BaseOperationRequest setBody(String body) {
        ((HttpPost) client).setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        return this;
    }

    protected BaseOperationRequest setHeader(String body) {
        ((HttpPost) client).setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        return this;
    }

    public void packageRequest() {

    }

    public void packageParams() {

    }

}
