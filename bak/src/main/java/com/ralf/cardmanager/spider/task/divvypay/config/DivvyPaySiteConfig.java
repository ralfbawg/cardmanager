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
    @Value("${cm.budget.id}")
    protected String budgetId = "";
}
