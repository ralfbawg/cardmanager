package com.ralf.cardmanager.spider.util;

import io.netty.handler.codec.base64.Base64Decoder;
import org.apache.http.message.BasicNameValuePair;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * @program: cardmanager
 * @description: Divvy64Decode
 * @author: Ralf Chen
 * @create: 2019-08-15 16:48
 **/
public class Base64Util {
    public static BasicNameValuePair toNameValuePair(String encodeStr) {
        try {
            String tmp = new String(new BASE64Decoder().decodeBuffer(encodeStr));
//            String tmp = new String(new BASE64Decoder().decodeBuffer("VXNlckFsbG9jYXRpb246NDY0MzUw"));
            if (tmp.contains(":")) {
                return new BasicNameValuePair(tmp.split(",")[0], tmp.split(":")[1]);
            } else {
                return new BasicNameValuePair("source", tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
