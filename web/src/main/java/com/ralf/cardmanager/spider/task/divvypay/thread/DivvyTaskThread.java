package com.ralf.cardmanager.spider.task.divvypay.thread;


import com.jeesite.common.lang.StringUtils;
import com.ralf.cardmanager.spider.task.SpiderBasicThread;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.service.DivvypayService;
import com.ralf.cardmanager.spider.util.SpiderUtil;
import com.ralf.cardmanager.spider.util.SpringContextUtil;
import com.ralf.cardmanager.spider.util.WebDriverPool;
import org.apache.ibatis.annotations.AutomapConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

public class DivvyTaskThread extends SpiderBasicThread implements Runnable {
    WebDriverPool webDriverPool;
    WebDriver webDriver;

    public DivvyTaskThread() {
        super(SpringContextUtil.getBean(DivvyPaySiteConfig.class));
        config.loginUrl = "https://app.divvy.co/login";
        config.requestHead = new HashMap() {{
            put(":authority", "app.divvy.co");
            put(":method", "POST");
            put(":path", "/je/graphql");
            put(":scheme", "https");
            put("accept", "*/*");
            put("accept-encoding", "gzip, deflate, br");
            put("authority", "app.divvy.co");
            put("accept-language", "zh-CN,zh;q=0.9");
            put("content-type", "application/json");
            put("origin", "https://app.divvy.co");
            put("referer", "https://app.divvy.co/home");
            put("x-api-version", "2");
            put("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3704.400 QQBrowser/10.4.3587.400");
            //TODO 差cookie useragent content-length
        }};
        webDriverPool = new WebDriverPool();
        webDriver = webDriverPool.getNew(WebDriverPool.DRIVER_CHROME);
        webDriver.get(config.loginUrl);
    }

    @Override
    public void run() {
        while (config.runFlag) {
            while (!config.Logined) {
                login();
            }

            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void doLogin() throws Exception {
        int loginTry = 3;
        while (!checkLoginStatus(--loginTry)) {
            Thread.sleep(10 * 1000);
        }
        ChromeDriver chrome = ((ChromeDriver) webDriver);
        chrome.executeScript("document.querySelector('input[name=email]').focus()");
        chrome.getKeyboard().sendKeys("22123971@qq.com");
        chrome.executeScript("document.querySelector('input[name=password]').focus()");
        chrome.getKeyboard().sendKeys("Wwkkvikthh1234");
        chrome.executeScript("document.querySelector('button.auth0-lock-submit').click();");
        while (!SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("i.SideNavigation-header-logo.Icon.Icon-logoSmall"))) {
//            service.sendShouldLoginByHandEmail();
            Thread.sleep(Long.valueOf(SpringContextUtil.getBean(DivvypayService.class).emailInterval) * 1000);
        }
        config.authToken = StringUtils.isEmpty(((ChromeDriver) webDriver).getSessionStorage().getItem("id_token")) ? ((ChromeDriver) webDriver).getLocalStorage().getItem("id_token") : ((ChromeDriver) webDriver).getSessionStorage().getItem("id_token");
        config.getRequestHead().put("authorization", "Bearer " + config.authToken);
        config.setLogined(true);
    }

    private boolean checkLoginStatus(int tryCount) {
        if (SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("input[name=email]")) && SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("input[name=password]"))) {
            return true;
        } else {
            config.setLogined(false);
            config.authToken = "";
            return false;
        }
    }
}
