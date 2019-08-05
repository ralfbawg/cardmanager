package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.ralf.cardmanager.spider.task.BaseOperation;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Scope
@Service
public class CreateCardByBudget extends BaseDivvyOperation {

    public CreateCardByBudget(DivvyPaySiteConfig config) {
        super(config);
    }


    @Override
    public void persistent(String rsp) {

    }

    @Override
    public String getUrl() {
        return null;
    }
}
