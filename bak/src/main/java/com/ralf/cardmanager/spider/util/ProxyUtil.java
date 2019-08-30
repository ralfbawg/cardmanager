package com.ralf.cardmanager.spider.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Ralf Chen on 2017/1/8 0008.
 */
public class ProxyUtil {
    private static Logger logger = LoggerFactory.getLogger(ProxyUtil.class);
    //线程上次获取时间
    private static ThreadLocal<Long> lastGetTime = new ThreadLocal<>();
    //线程上次获取代理
    private static ThreadLocal<String> lastGetProxy = new ThreadLocal<>();

    public static volatile Queue<String> proxyList = new LinkedList<String>();

    public static volatile Queue<String> proxyUsedList = new LinkedList<String>();

    public static final Long proxyDefaultInteval = 5l;

    private static ReentrantLock lock = new ReentrantLock();

    private static Date lastGetProxyTime;

    //    private static String getProxyUrl = "http://dev.kuaidaili.com/api/getproxy/?orderid=938386199175056&num=100&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=1&method=2&an_an=1&an_ha=1&sp1=1&sp2=1&quality=1&sort=1&dedup=1&sep=4";
//    private static String getProxyUrl = "http://dev.kuaidaili.com/api/getproxy/?orderid=938386199175056&num=100&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=1&method=2&an_an=1&an_ha=1&sp1=1&sp2=1&quality=1&sort=1&sep=4";
    private static String getProxyUrl = "http://dev.kuaidaili.com/api/getproxy/?orderid=978691296561022&num=100&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=1&method=2&an_an=1&an_ha=1&sp1=1&sp2=1&quality=1&sort=1&sep=4";

    public static String getProxy() {
        return getProxy(proxyDefaultInteval);
    }

    public static String getProxy(Long proxyInteval) {
        Date now = new Date();
        lock.lock();
        try {
            if (lastGetTime.get() == null) {
                getProxyAndSetLastTime(now);

            } else {
                if (now.getTime() - lastGetTime.get() > proxyInteval * 1000) {
                    getProxyAndSetLastTime(now);
                } else {//还没到切换时间
                    return lastGetProxy.get();
                }
            }

            String proxy = proxyList.poll();
            while (!testProxyAvailable(proxy)) {
                proxy = proxyList.poll();
            }
            proxyUsedList.offer(proxy);
            lastGetProxy.set(proxy);
            return proxy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }

    }

    private static boolean testProxyAvailable(String proxy) {
        try {
            URL url = new URL("http://www.baidu.com");
            // 创建代理服务器
            InetSocketAddress addr = new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1]));
            Proxy proxyServer = new Proxy(Proxy.Type.HTTP, addr); // http 代理

            URLConnection conn = url.openConnection(proxyServer);
            if (conn.getContentLength() > 0) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        httpClient.getHostConfiguration().setProxy(proxy.split(":")[0], proxy.split(":")[1]);

        return false;
    }


    public static List<String> getProxiesByRemote() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet method = new HttpGet(getProxyUrl);
        method.addHeader("Content-type", "application/json; charset=utf-8");
        method.setHeader("Accept", "application/json");
        HttpResponse response;
        long startTime = System.currentTimeMillis();
        try {
            response = httpClient.execute(method);
            long endTime = System.currentTimeMillis();
            int statusCode = response.getStatusLine().getStatusCode();

            logger.info("statusCode:" + statusCode);
            logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
            String body = EntityUtils.toString(response.getEntity());
            if (body != null && body.contains("|")) {
                return Arrays.asList(body.split("\\|"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> list = getProxiesByRemote();
        for (String s : list) {
            System.out.println("the proxy info:" + s);
        }

    }

    private static void getProxyAndSetLastTime(Date now) {
        if (proxyList.size() == 0 || lastGetTime.get() == null || (proxyList.size() < 20 && now.getTime() - lastGetTime.get() > 5000)) {
            List<String> proxies = getProxiesByRemote();
            for (String proxy : proxies) {
                proxyList.offer(proxy);
            }
            lastGetTime.set(System.currentTimeMillis());
        }
    }

}
