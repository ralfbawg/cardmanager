package com.ralf.cardmanager.spider.task.divvypay.service;

import com.jeesite.common.msg.EmailUtils;
import com.jeesite.modules.msg.send.EmailSendService;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.thread.DivvyTaskThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class DivvypayService {
    @Value("${cm.spider.loginTip.email.username}")
    private String emailUsername;
    @Value("${cm.spider.loginTip.email.password}")
    private String emailPassword;
    @Value("${cm.spider.loginTip.email.fromAddress}")
    private String emailFromAddress;
    @Value("${cm.spider.loginTip.email.ssl}")
    private String emailSsl;
    @Value("${cm.spider.loginTip.interval}")
    public String emailInterval;
    @Autowired
    EmailSendService emailSendService;
    @Autowired
    DivvyPaySiteConfig divvyConfig;

    @PostConstruct
    public void init() {
        log.debug("i am come in");
        divvyConfig.shouldLogin = true;
    }


    public void sendShouldLoginByHandEmail() {
        EmailUtils.send(emailFromAddress, emailPassword, "qq.com", "true", "23", "22123971@qq.com", "需要登录了", "需要登录了");
    }
}
