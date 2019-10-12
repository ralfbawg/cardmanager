package com.ralf.cardmanager.spider.task.epaylinks.service;

import com.jeesite.common.msg.EmailUtils;
import com.jeesite.modules.msg.send.EmailSendService;
import com.ralf.cardmanager.spider.task.epaylinks.config.EpaylinksSiteConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class EpaylinksService {
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
    EpaylinksSiteConfig divvyConfig;

    @PostConstruct
    public void init() {
        log.debug("i am come in");
        divvyConfig.shouldLogin = true;
    }


    public void sendShouldLoginByHandEmail() {
        EmailUtils.send(emailFromAddress, emailPassword, "qq.com", "true", "23", "22123971@qq.com", "需要登录了", "需要登录了");
    }
}
