package com.jeesite.modules;

import lombok.val;

/**
 * @program: cardmanager
 * @description: 测试
 * @author: Ralf Chen
 * @create: 2019-09-06 20:38
 **/
public class Main {
    public static void main(String[] args) {
        int pageSize = 20;
        int test = 32550;
        for (int i = 0; i < (test % pageSize > 0 ? 1 : 0) + test / pageSize; i++) {
            System.out.println(i * pageSize);
        }
    }
}
