package com.ralf.cardmanager.spider.task;

import com.ralf.cardmanager.spider.util.WebDriverPool;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public abstract class SpiderBasicThread {
    protected SiteBaseConfig config;

    public static final int poolSize = 5;

    public static WebDriverPool webDriverPool;


    public SpiderBasicThread(SiteBaseConfig config) {
        this.config = config;
    }

    public void checkInit() {
        if (webDriverPool == null) {
            synchronized (this) {
                webDriverPool = new WebDriverPool(poolSize);
            }
        }
    }

    protected WebDriver getWebDriver(String type) throws InterruptedException {
        checkInit();
        return webDriverPool.getNew(type);
    }


    public void login() {
        if (config.shouldLogin && !config.Logined) {
            try {
                doLogin();
            } catch (Exception e) {
                log.debug("登录出错了");
                e.printStackTrace();
            }
        }
    }

    public abstract void doLogin() throws Exception;
}
