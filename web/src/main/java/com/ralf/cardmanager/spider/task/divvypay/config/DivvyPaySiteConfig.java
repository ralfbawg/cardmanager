package com.ralf.cardmanager.spider.task.divvypay.config;

import com.ralf.cardmanager.spider.task.SiteBaseConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper = true)
@Component("DivvySiteConfig")
public class DivvyPaySiteConfig extends SiteBaseConfig {
    @Value("${cm.divvy.budget.id}")
    protected String budgetId = "";
    @Value("${cm.divvy.company.id}")
    protected String companyId = "";
    @Value("${cm.divvy.username}")
    protected String username = "";
    @Value("${cm.divvy.password}")
    protected String password = "";
    @Value("${cm.divvy.budget.ownerId}")
    protected String budgetOwnerId = "";
    @Value("${cm.divvy.api.version}")
    protected String apiVersion = "";
}
