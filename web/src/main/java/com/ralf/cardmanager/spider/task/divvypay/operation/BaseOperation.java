package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.jeesite.common.web.http.HttpClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public abstract class BaseOperation {




    private BaseOperationRequest request;

    private String defaultUserAgent = "";

    private String defaultUrl = "https://www.divvypay.co/js/graph";

    public abstract BaseOperationResponse excute();


    public abstract void persistent();


}
