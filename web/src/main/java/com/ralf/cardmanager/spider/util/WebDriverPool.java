package com.ralf.cardmanager.spider.util;

import com.jeesite.common.lang.StringUtils;
import lombok.val;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ralf Chen on 2017/1/3 0003.
 */
public class WebDriverPool {
    private Logger logger = Logger.getLogger(getClass());

    private final static int DEFAULT_CAPACITY = 5;

    private final int capacity;

    private final static int STAT_RUNNING = 1;

    private final static int STAT_CLODED = 2;

    private AtomicInteger stat = new AtomicInteger(STAT_RUNNING);

    /*
     * new fields for configuring phantomJS
     */
    private WebDriver mDriver = null;
    private boolean mAutoQuitDriver = true;

    private static final String CONFIG_FILE = "config.ini";
    public static final String DRIVER_FIREFOX = "firefox";
    public static final String DRIVER_IE = "ie";
    public static final String DRIVER_CHROME = "chrome";
    public static final String DRIVER_PHANTOMJS = "phantomjs";

    protected static Properties sConfig;
    protected static DesiredCapabilities sCaps;


    /**
     * Configure the GhostDriver, and initialize a WebDriver instance. This part
     * of code comes from GhostDriver.
     * https://github.com/detro/ghostdriver/tree/master/test/java/src/test/java/ghostdriver
     *
     * @throws IOException
     * @author bob.li.0718@gmail.com
     */
    public void configure(String driverParam) throws IOException {
        // Read config file
        sConfig = new Properties();
//        sConfig.load(new FileReader(CONFIG_FILE));
        sConfig.load(new InputStreamReader(new DefaultResourceLoader().getResource(CONFIG_FILE).getInputStream(), "UTF-8"));

        // Prepare capabilities
        sCaps = DesiredCapabilities.internetExplorer();
        sCaps.setJavascriptEnabled(true);
        sCaps.setCapability("takesScreenshot", false);
        sCaps.setCapability("loadImages", false);
//        sCaps.loadImages();

        String driver = StringUtils.isEmpty(driverParam) ? sConfig.getProperty("driver", DRIVER_PHANTOMJS) : driverParam;

        // Fetch PhantomJS-specific configuration parameters
        if (driver.equals(DRIVER_PHANTOMJS)) {
            // "phantomjs_exec_path"
            if (sConfig.getProperty("phantomjs_exec_path") != null) {
                sCaps.setCapability(
                        PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                        sConfig.getProperty("phantomjs_exec_path"));
            } else {
                throw new IOException(
                        String.format(
                                "Property '%s' not set!",
                                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY));
            }
            // "phantomjs_driver_path"
            if (sConfig.getProperty("phantomjs_driver_path") != null) {
                System.out.println("Test will use an external GhostDriver");
                sCaps.setCapability(
                        PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY,
                        sConfig.getProperty("phantomjs_driver_path"));
            } else {
                System.out
                        .println("Test will use PhantomJS internal GhostDriver");
            }
        }

        // Disable "web-security", enable all possible "ssl-protocols" and
        // "ignore-ssl-errors" for PhantomJSDriver
        // sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new
        // String[] {
        // "--web-security=false",
        // "--ssl-protocol=any",
        // "--ignore-ssl-errors=true"
        // });

        ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
                cliArgsCap);

        // Control LogLevel for GhostDriver, via CLI arguments
        sCaps.setCapability(
                PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
                new String[]{"--logLevel="
                        + (sConfig.getProperty("phantomjs_driver_loglevel") != null ? sConfig
                        .getProperty("phantomjs_driver_loglevel")
                        : "INFO")});

        // String driver = sConfig.getProperty("driver", DRIVER_PHANTOMJS);
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", UserAgentUtil.getRandomUserAgent(""));

        if (sConfig.getProperty("userProxy").equals("true")) {
            Proxy proxy = new Proxy();
            String proxyIpAndPort = ProxyUtil.getProxy();
            proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
            sCaps.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
            sCaps.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
            System.setProperty("http.nonProxyHosts", "localhost");
            sCaps.setCapability(CapabilityType.PROXY, proxy);
        }

        // Start appropriate Driver
        if (isUrl(driver)) {
            sCaps.setBrowserName("phantomjs");
            mDriver = new RemoteWebDriver(new URL(driver), sCaps);
        } else if (driver.equals(DRIVER_FIREFOX)) {
            System.setProperty("webdriver.firefox.driver", sConfig.getProperty("webdriver.firefox.driver"));
            mDriver = new FirefoxDriver(sCaps);
        } else if (driver.equals(DRIVER_CHROME)) {
            System.setProperty("webdriver.chrome.driver", sConfig.getProperty("webdriver.chrome.driver"));
            mDriver = new ChromeDriver(sCaps);
        } else if (driver.equalsIgnoreCase(DRIVER_IE)) {
            System.setProperty("webdriver.ie.driver", sConfig.getProperty("webdriver.ie.driver"));
            mDriver = new InternetExplorerDriver(sCaps);
        } else if (driver.equals(DRIVER_PHANTOMJS)) {
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "browserName", "chrome");
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "loadImages", "false");
            mDriver = new PhantomJSDriver(sCaps);
        }
    }

    /**
     * check whether input is a valid URL
     *
     * @param urlString urlString
     * @return true means yes, otherwise no.
     * @author bob.li.0718@gmail.com
     */
    private boolean isUrl(String urlString) {
        try {
            new URL(urlString);
            return true;
        } catch (MalformedURLException mue) {
            return false;
        }
    }

    /**
     * store webDrivers created
     */
    private List<WebDriver> webDriverList = Collections
            .synchronizedList(new ArrayList<WebDriver>());

    /**
     * store webDrivers available
     */
    private BlockingDeque<WebDriver> innerQueue = new LinkedBlockingDeque<WebDriver>();

    public WebDriverPool(int capacity) {
        this.capacity = capacity;
    }

    public WebDriverPool() {
        this(DEFAULT_CAPACITY);
    }

    public WebDriver getNew(String driver) {
        synchronized (this) {
            try {
                configure(driver);
                return mDriver;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * @return
     * @throws InterruptedException
     */
    public WebDriver get() throws InterruptedException {
        checkRunning();
        WebDriver poll;
        poll = innerQueue.poll();
        if (poll != null) {
            return poll;
        }
        if (webDriverList.size() < capacity) {
            synchronized (webDriverList) {
                if (webDriverList.size() < capacity) {

                    // add new WebDriver instance into pool
                    try {
                        configure("");
                        innerQueue.add(mDriver);
                        webDriverList.add(mDriver);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // ChromeDriver e = new ChromeDriver();
                    // WebDriver e = getWebDriver();
                    // innerQueue.add(e);
                    // webDriverList.add(e);
                }
            }

        }
        return innerQueue.take();
    }

    public void returnToPool(WebDriver webDriver) throws InterruptedException {
        innerQueue.remove(webDriver);
        synchronized (webDriverList) {
            for (WebDriver driver : webDriverList) {
                if (webDriver.equals(driver)) {
                    webDriverList.remove(webDriver);
                }
            }
        }
        webDriver.quit();
//        checkRunning();
        innerQueue.putLast(this.get());
    }

    protected void checkRunning() {
        if (!stat.compareAndSet(STAT_RUNNING, STAT_RUNNING)) {
            throw new IllegalStateException("Already closed!");
        }
    }

    public void closeAll() {
        boolean b = stat.compareAndSet(STAT_RUNNING, STAT_CLODED);
        if (!b) {
            throw new IllegalStateException("Already closed!");
        }
        for (WebDriver webDriver : webDriverList) {
            logger.info("Quit webDriver" + webDriver);
            webDriver.quit();
            webDriver = null;
        }
    }
}
