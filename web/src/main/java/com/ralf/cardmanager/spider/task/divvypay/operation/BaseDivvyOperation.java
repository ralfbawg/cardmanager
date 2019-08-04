package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.jeesite.common.web.http.HttpClientUtils;
import com.ralf.cardmanager.spider.task.BaseOperation;
import com.ralf.cardmanager.spider.task.SiteBaseConfig;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.util.SpringContextUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseDivvyOperation extends BaseOperation {
    public BaseDivvyOperation(DivvyPaySiteConfig config) {
        defaultHeader.put("authorization", "Bearer " + SpringContextUtil.getBean( DivvyPaySiteConfig.class).authToken);
    }

    protected String defaultUrl = "https://www.divvypay.co/js/graph";
    protected String defaultUserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3704.400 QQBrowser/10.4.3587.400";
    protected Map defaultHeader = new HashMap<String, String>() {{
        put("authority", "app.divvy.co");
        put("method", "POST");
        put("path", "/je/graphql");
        put("scheme", "https");
        put("accept", "*/*");
        put("accept-encoding", "gzip, deflate, br");
        put("accept-language", "zh-CN,zh;q=0.9");
        put("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2RpdnZ5LXByZC5hdXRoMC5jb20vIiwic3ViIjoiYXV0aDB8NWNmOGQ4NDcwN2RiYmUwYzY5NWVlY2VmIiwiYXVkIjoiaHR0cHM6Ly9iYWNrZW5kLmRpdnZ5cGF5LmNvbS9ncmFwaHFsIiwiaWF0IjoxNTY0NTk1OTU1LCJleHAiOjE1NjQ2MDMxNTUsImF6cCI6IjYwYVdpZmpybGFLTHpIcEhYVVhwZE5nVVIwanpueng3Iiwic2NvcGUiOiIifQ.U20GLp3F5ziZrwUH8bsya1mZ3y3yTKCVkxq2hK3kooI");
        put("content-type", " application/json");
        put("cookie", " _ga=GA1.2.87993226.1563987455; _pendo_accountId.063f9b98-4d82-4b70-48d1-1e82e1fa3973=Q29tcGFueTozMDI3; _pendo_visitorId.063f9b98-4d82-4b70-48d1-1e82e1fa3973=VXNlcjo1MTIzNg%3D%3D; _gid=GA1.2.734103677.1564589193; _pendo_meta.063f9b98-4d82-4b70-48d1-1e82e1fa3973=1986949279; _gat=1; AWSALB=9Uefnz6V2WIyaTkqAU7CooRCOxPQYKQyTg8ZEeYD00bytbUYM1N0d2HSfRoH0Ug8gpQ0erzieOpyPefixfs37spf1zF3jaxxHiH6MldBX+1uXdbqJElcBOo1ltgw2ZouehsjeffW4NmbMASZMOEB5I2JKAKXqGSl6Iff95WhdVRXCZUJ7F0SBFInPx94tGDe23tmI5IBUSp7umV9ZkKJavCD99Sj20uJyg3OMVltiyzIWpoRPOJiqPnE8bbc3IU=; intercom-session-gh17um10=TXZQWUxDdEM4VEZvbkdyQnMwREFRLysrNHpQOHMxRXBrSjBURkQ3bUszWnNBRGw2V0pSb0p6a2kwdE1oOGlySS0tUk02Rm92ZmhyVGNhUGVJQlBnaG1mdz09--dbce332ab9c07930d71e7e2d33e9722a4393b682");
        put("origin", " https://app.divvy.co");
        put("referer", "https://app.divvy.co/budgets/QnVkZ2V0OjQ3MzAz/virtual-cards");
        put("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3704.400 QQBrowser/10.4.3587.400");
        put("x-api-version", " 2");
    }};


}
