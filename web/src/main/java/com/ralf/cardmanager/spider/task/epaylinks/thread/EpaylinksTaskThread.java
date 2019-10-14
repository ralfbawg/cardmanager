package com.ralf.cardmanager.spider.task.epaylinks.thread;


import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.msg.EmailUtils;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.msg.send.EmailSendService;
import com.ralf.cardmanager.spider.task.SpiderBasicThread;
import com.ralf.cardmanager.spider.task.epaylinks.config.EpaylinksSiteConfig;
import com.ralf.cardmanager.spider.util.SpiderUtil;
import com.ralf.cardmanager.spider.util.SpringContextUtil;
import com.ralf.cardmanager.spider.util.WebDriverPool;
import com.ralf.cardmanager.system.mail.QQMailUtil;
import lombok.val;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;

public class EpaylinksTaskThread extends SpiderBasicThread implements Runnable {
    WebDriverPool webDriverPool;
    WebDriver webDriver;

    public EpaylinksTaskThread() {
        super(SpringContextUtil.getBean(EpaylinksSiteConfig.class));
        config.loginUrl = "https://merchant.globalcash.hk/www/login.jsp";
        config.requestHead = new HashMap() {{
            put(":method", "POST");
            put(":Cookie", "route=e062ed5a095364733e85c54c9ce5238f; JSESSIONID=344394648C676C7FC75BAAF40A71C564.node919");
            put("accept", "*/*");
            put("accept-encoding", "gzip, deflate, br");
            put("accept-language", "zh-CN,zh;q=0.9");
            put("content-type", "application/x-www-form-urlencoded; charset=utf-8");
            put("Host", "https://merchant.globalcash.hk");
            put("referer", "https://merchant.globalcash.hk/space/space.do");
            put("X-Requested-With", "XMLHttpRequest");
            put("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3704.400 QQBrowser/10.4.3587.400");
            //TODO 差cookie useragent content-length
        }};
//        webDriverPool = new WebDriverPool();
//        webDriver = webDriverPool.getNew(WebDriverPool.DRIVER_CHROME);
        webDriver = createSingalWebDriver(WebDriverPool.DRIVER_CHROME);
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

        do {
            Thread.sleep(1 * 1000);
            webDriver.get(config.loginUrl);
            Thread.sleep(3 * 1000);
        } while (!checkLoginStatus(--loginTry));

        ChromeDriver chrome = ((ChromeDriver) webDriver);
        Thread.sleep(200);
        chrome.findElement(new By.ById("account1")).sendKeys(config.getUsername());
        Thread.sleep(200);
        chrome.findElement(new By.ById("_ocx_password")).sendKeys(config.getPassword());
                    while (!checkAndFillMailVerifyCode(chrome)) {
            Thread.sleep(1 * 1000);
        }
//        chrome.executeScript("document.querySelector('a.orangeBtn-login').click();");//登录
        int count = 3;
        while (!SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("i.SideNavigation-header-logo.Icon.Icon-logoSmall")) && count-- > 0) {
//            service.sendShouldLoginByHandEmail();
            Thread.sleep(3 * 1000);
        }
        if (!SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("i.SideNavigation-header-logo.Icon.Icon-logoSmall")) && count <= 0) {
            return;
        }

        config.authToken = StringUtils.isEmpty(((ChromeDriver) webDriver).getSessionStorage().getItem("id_token")) ? ((ChromeDriver) webDriver).getLocalStorage().getItem("id_token") : ((ChromeDriver) webDriver).getSessionStorage().getItem("id_token");
        config.getRequestHead().put("authorization", "Bearer " + config.authToken);
        config.setLogined(true);
    }

    private boolean checkLoginStatus(int tryCount) {
//        if (SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("input[name=email]")) && SpiderUtil.doesWebElementExist(webDriver, new By.ByCssSelector("input[name=password]"))) {
        if (SpiderUtil.doesWebElementExist(webDriver, new By.ById("_ocx_password")) && SpiderUtil.doesWebElementExist(webDriver, new By.ById("account1"))) {
            return true;
        } else {
            config.setLogined(false);
            config.authToken = "";
            return false;
        }
    }

    private Boolean checkAndFillMailVerifyCode(ChromeDriver chrome) throws Exception {
        chrome.executeScript("document.querySelector('a.validation').click();");//发验证码
        Alert alert = chrome.switchTo().alert();
        alert.accept();
        val eConfig = (EpaylinksSiteConfig) config;
        SpringUtils.getBean(QQMailUtil.class).reviceQQEmail(eConfig.getEmailUsername(), eConfig.getEmailPassword());
        chrome.findElement(new By.ById("account1")).sendKeys("test");
        return true;
    }

    private boolean checkLoginedStatus() {
        return false;
    }
}
