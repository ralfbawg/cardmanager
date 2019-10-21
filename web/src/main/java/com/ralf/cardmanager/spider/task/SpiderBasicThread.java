package com.ralf.cardmanager.spider.task;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.spider.util.WebDriverPool;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.ibatis.annotations.Case;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.springframework.core.io.DefaultResourceLoader;
import sun.security.provider.ConfigFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public abstract class SpiderBasicThread {

    protected SiteBaseConfig config;

    public static final int poolSize = 5;

    public static WebDriverPool webDriverPool;

    public static Properties webDriverConfig = new Properties();

    public static String CONFIG_FILE = "config.ini";

    static {
        try {
            webDriverConfig.load(new InputStreamReader(new DefaultResourceLoader().getResource(CONFIG_FILE).getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        sConfig.load(new FileReader(CONFIG_FILE));


    public SpiderBasicThread(SiteBaseConfig config) {
        String a = SpringUtils.getApplicationContext().getEnvironment().getProperty("cm.env");
        if (!StringUtils.isEmpty(a) && a.equalsIgnoreCase("dev")) {
            CONFIG_FILE = "config-dev.ini";
        }
        try {
            webDriverConfig.load(new InputStreamReader(new DefaultResourceLoader().getResource(CONFIG_FILE).getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    protected WebDriver createSingalWebDriver(String type) {
        switch (type.toLowerCase()) {
            case WebDriverPool.DRIVER_CHROME:
            default:
                val option = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<String, Object>() {{
                    put("credentials_enable_service", false);
                    put("profile.password_manager_enabled", false);
                    put("profile.default_content_setting_values.notifications", 2);

                }};
                option.setExperimentalOption("prefs", prefs);
//                option.setBinary(webDriverConfig.getProperty("chrome.binary.path"));
//                option.addArguments("start-maximized"); // open Browser in maximized mode
                option.addArguments("disable-infobars"); // disabling infobars
                option.addArguments("no-sandbox");
//                option.addArguments("user-data-dir=D:/UserDataFolder");
//                option.addArguments("--disable-extensions"); // disabling extensions
                option.addArguments("--disable-gpu"); // applicable to windows os only
//                option.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
                option.setExperimentalOption("useAutomationExtension", false);
                option.addArguments("blink-settings", "imagesEnabled=false");
                System.setProperty("webdriver.chrome.driver", webDriverConfig.getProperty("webdriver.chrome.driver"));
                return new ChromeDriver(option);
            case WebDriverPool.DRIVER_IE:
                val ieOption = new InternetExplorerOptions();
                return new InternetExplorerDriver(ieOption);
        }
    }

    public void login() {
        if (config.shouldLogin && !config.Logined) {
            try {
                doLogin();
            } catch (Exception e) {
                log.error("登录出错了");
                e.printStackTrace();
            }
        }
    }

    public abstract void doLogin() throws Exception;
}
