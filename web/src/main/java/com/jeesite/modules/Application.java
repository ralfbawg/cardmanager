/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules;

import com.ralf.cardmanager.spider.task.divvypay.thread.DivvyTaskThread;
import com.ralf.cardmanager.spider.task.epaylinks.thread.EpaylinksTaskThread;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application
 *
 * @author ThinkGem
 * @version 2018-10-13
 */
@SpringBootApplication
@EnableScheduling
@ComponentScans(value = {
        @ComponentScan("com.ralf"), @ComponentScan("com.jeesite")
})
@Slf4j
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        log.debug("i am starting");
        val context = SpringApplication.run(Application.class, args);
        if (context.getEnvironment().getRequiredProperty("cm.spider.enabled", boolean.class)) {
            new Thread(new DivvyTaskThread()).start();
//            new Thread(new EpaylinksTaskThread()).start();
        }

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        this.setRegisterErrorPageFilter(false); // 错误页面有容器来处理，而不是SpringBoot
        return builder.sources(Application.class);
    }

}