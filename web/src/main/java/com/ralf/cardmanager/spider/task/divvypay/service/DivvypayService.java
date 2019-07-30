package com.ralf.cardmanager.spider.task.divvypay.service;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.thread.DivvyTaskThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Lazy(false)
@Slf4j
public class DivvypayService {
    @PostConstruct
    public void init() {
        log.debug("i am come in");
        DivvyPaySiteConfig divvyConfig = new DivvyPaySiteConfig();
        divvyConfig.shouldLogin = true;
        divvyConfig.username = "22123971@qq.com";
        divvyConfig.password = "Wwkkvikthh1234";

        new Thread(new DivvyTaskThread(divvyConfig)).start();
    }

}
