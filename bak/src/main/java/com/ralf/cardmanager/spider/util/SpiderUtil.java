package com.ralf.cardmanager.spider.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class SpiderUtil {
    private static Logger logger = LoggerFactory.getLogger(SpiderUtil.class);

    /**
     * 下载图片到本地
     *
     * @param picUrl    图片Url
     * @param localPath 本地保存图片地址
     * @return
     */
    public static String downloadPicForNews(String picUrl, String localPath) {
        String filePath = "";
        String url = "";
        try {
            URL httpurl = new URL(picUrl);
            HttpURLConnection urlcon = (HttpURLConnection) httpurl.openConnection();
            urlcon.setReadTimeout(3000);
            urlcon.setConnectTimeout(3000);
            int state = urlcon.getResponseCode(); //图片状态
            if (state == 200) {
                String fileName = getFileNameFromUrl(picUrl);
                filePath = localPath + fileName;
                File f = new File(filePath);
                FileUtils.copyURLToFile(httpurl, f);
//                url = filePath.replace("/www/web/imgs", fun.getProValue("IMG_PATH"));
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
        return url;
    }

    /**
     * 替换内容中图片地址为本地地址
     *
     * @param content html内容
     * @param pic_dir 本地地址文件路径
     * @return html内容
     */
    public static String replaceForNews(String content, String pic_dir) {
        String str = content;
        String cont = content;
        while (true) {
            int i = str.indexOf("src=\"");
            if (i != -1) {
                str = str.substring(i + 5, str.length());
                int j = str.indexOf("\"");
                String pic_url = str.substring(0, j);
                //下载图片到本地并返回图片地址
                String pic_path = downloadPicForNews(pic_url, pic_dir);
                if (StringUtils.isNotEmpty(pic_url) && StringUtils.isNotEmpty(pic_path)) {
                    cont = cont.replace(pic_url, pic_path);
                    str = str.substring(j, str.length());
                }
            } else {
                break;
            }
        }
        return cont;
    }

    /**
     * 下载图片到本地
     *
     * @param picUrl    图片Url
     * @param localPath 本地保存图片地址
     * @return
     */
    public static String downloadPic(String picUrl, String localPath) {
        String filePath = null;
        String url = null;
        try {
            URL httpurl = new URL(picUrl);
            String fileName = getFileNameFromUrl(picUrl);
            filePath = localPath + fileName;
            File f = new File(filePath);
            FileUtils.copyURLToFile(httpurl, f);
//            Function fun = new Function();
//            url = filePath.replace("/www/web/imgs", fun.getProValue("IMG_PATH"));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
        return url;
    }

    /**
     * 根据url获取文件名
     *
     * @param url
     * @return 文件名
     */
    public static String getFileNameFromUrl(String url) {
        //获取后缀
        String sux = url.substring(url.lastIndexOf("."));
        if (sux.length() > 4) {
            sux = ".jpg";
        }
        int i = (int) (Math.random() * 1000);
        //随机时间戳文件名称
        String name = new Long(System.currentTimeMillis()).toString() + i + sux;
        return name;
    }

    public static boolean doesWebElementExist(WebDriver driver, By selector) {

        try {
            driver.findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean doesWebElementExist(WebElement element, By selector) {

        try {
            element.findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


}
