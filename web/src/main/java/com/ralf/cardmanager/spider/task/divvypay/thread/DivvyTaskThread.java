package com.ralf.cardmanager.spider.task.divvypay.thread;


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

    }

    @Override
    public void run() {
        login();
    }

    @Override
    public void doLogin() throws Exception {
        webDriver.get(config.loginUrl);
        while (!checkLoginStatus()) {
            Thread.sleep(1 * 1000);
        }
        ChromeDriver chrome = ((ChromeDriver) webDriver);
//        String event = "function fireKeyEvent(el, evtType, keyCode){ var doc = el.ownerDocument, win = doc.defaultView || doc.parentWindow, evtObj; if(doc.createEvent){ if(win.KeyEvent) { evtObj = doc.createEvent('KeyEvents'); evtObj.initKeyEvent( evtType, true, true, win, false, false, false, false, keyCode, 0 ); } else { evtObj = doc.createEvent('UIEvents'); Object.defineProperty(evtObj, 'keyCode', {         get : function() { return this.keyCodeVal; }     });          Object.defineProperty(evtObj, 'which', {         get : function() { return this.keyCodeVal; }     }); evtObj.initUIEvent( evtType, true, true, win, 1 ); evtObj.keyCodeVal = keyCode; if (evtObj.keyCode !== keyCode) {            } } el.dispatchEvent(evtObj); }  else if(doc.createEventObject){ evtObj = doc.createEventObject(); evtObj.keyCode = keyCode; el.fireEvent('on' + evtType, evtObj); } } ";
//        chrome.executeScript(event + " fireKeyEvent(document.querySelector('input[name=email]'), 'keydown', 65);");
        chrome.executeScript("document.querySelector('input[name=email]').focus()");
        chrome.getKeyboard().sendKeys("22123971@qq.com");
//        chrome.executeScript("document.querySelector(\"input[name=email]\").value=\"" + config.username + "\";");
        chrome.executeScript("document.querySelector('input[name=password]').focus()");
        chrome.getKeyboard().sendKeys("Wwkkvikthh1234");
//        chrome.executeScript("document.querySelector(\"input[name=password]\").value=\"" + config.password + "\";");
        chrome.executeScript("document.querySelector('button.auth0-lock-submit').click();");
        while (!SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("i.SideNavigation-header-logo.Icon.Icon-logoSmall"))) {
//            service.sendShouldLoginByHandEmail();
//            Thread.sleep(Long.valueOf(service.emailInterval) * 1000);
        }
        config.authToken = ((ChromeDriver) webDriver).getSessionStorage().getItem("id_token");
        config.getRequestHead().put("authorization", "Bearer " + config.authToken);
        config.setLogined(true);
    }

    private boolean checkLoginStatus() {
        if (SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("input[name=email]")) && SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("input[name=password]"))) {
            return true;
        } else {
            config.setLogined(false);
            config.authToken = "";
            return false;
        }
    }
}
