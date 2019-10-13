package com.ralf.cardmanager.spider.task.epaylinks.config;

import com.ralf.cardmanager.spider.task.SiteBaseConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper = true)
@Component("EpaylinksSiteConfig")
public class EpaylinksSiteConfig extends SiteBaseConfig {

    @Value("${cm.epaylinks.username}")
    protected String username = "";
    @Value("${cm.epaylinks.password}")
    protected String password = "";
    @Value("${cm.epaylinks.email.username}")
    protected String emailUsername = "";
    @Value("${cm.epaylinks.email.password}")
    protected String emailPassword = "";
    @Value("${cm.epaylinks.email.host}")
    protected String emailHost = "";
    @Value("${cm.epaylinks.email.port}")
    protected String emailPort = "";
    @Value("${cm.epaylinks.email.authCode}")
    protected String emailAuthCode = "";
}
