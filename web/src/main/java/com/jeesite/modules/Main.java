package com.jeesite.modules;

import lombok.val;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @program: cardmanager
 * @description: 测试
 * @author: Ralf Chen
 * @create: 2019-09-06 20:38
 **/
public class Main {
    public static void main(String[] args) {
//        int pageSize = 20;
//        int test = 32550;
//        for (int i = 0; i < (test % pageSize > 0 ? 1 : 0) + test / pageSize; i++) {
//            System.out.println(i * pageSize);
//        }
//        val t = new DateTime();
//        val t = new DateTime(2019,12,1,1,1);
//        val start = (new DateTime(t.getYear(), t.getMonthOfYear(), 1, 8, 0).getMillis()/1000);
//        val end = (new DateTime((t.getMonthOfYear()!=12?0:1)+t.getYear(), (t.getMonthOfYear()==12?-11:1)+t.getMonthOfYear(), 1, 8, 0).getMillis()/1000)-1;
//        System.out.printf("start:" + start + ",end:" + end);
        System.out.println(float2("祂卯爴"));
        System.out.println(float3("祂卯爴"));
    }
    public static String float2(Object s2) {
        int var10000 = (3 ^ 5) << 4 ^ 5;
        int var10001 = (2 ^ 5) << 3 ^ 1;
        int var10002 = 2 << 3 ^ 2;
        String s;
        int var10003 = (s = (String)s2).length();
        char[] var10004 = new char[var10003];
        boolean var10006 = true;
        int var5 = var10003 - 1;
        var10003 = var10002;
        int var3;
        var10002 = var3 = var5;
        char[] var1 = var10004;
        int var4 = var10003;
        var10000 = var10002;

        for(int var2 = var10001; var10000 >= 0; var10000 = var3) {
            var10001 = var3;
            char var7 = s.charAt(var3);
            --var3;
            var1[var10001] = (char)(var7 ^ var2);
            if (var3 < 0) {
                break;
            }

            var10002 = var3--;
            var1[var10002] = (char)(s.charAt(var10002) ^ var4);
        }

        return new String(var1);
    }
    public static String float3(Object s2) {
        int var10000 = 1 << 3 ^ 2;
        int var10001 = 5 << 4 ^ 5;
        int var10002 = 5 << 4 ^ (3 ^ 5) << 1;
        String s;
        int var10003 = (s = (String)s2).length();
        char[] var10004 = new char[var10003];
        boolean var10006 = true;
        int var5 = var10003 - 1;
        var10003 = var10002;
        int var3;
        var10002 = var3 = var5;
        char[] var1 = var10004;
        int var4 = var10003;
        var10000 = var10002;

        for(int var2 = var10001; var10000 >= 0; var10000 = var3) {
            var10001 = var3;
            char var7 = s.charAt(var3);
            --var3;
            var1[var10001] = (char)(var7 ^ var2);
            if (var3 < 0) {
                break;
            }

            var10002 = var3--;
            var1[var10002] = (char)(s.charAt(var10002) ^ var4);
        }

        return new String(var1);
    }
}
