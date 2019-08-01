package com.ralf.cardmanager.spider.task.divvypay.operation;

public abstract class BaseOperation {
    private String defaultUserAgent = "";

    private String defaultUrl = "https://www.divvypay.co/js/graph";

    public abstract BaseOperationResponse excute();

    public abstract void persistent();

}
