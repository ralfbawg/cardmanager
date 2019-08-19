package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import org.springframework.stereotype.Service;

@Service
public class KeepSession extends BaseDivvyOperation {
    public KeepSession(DivvyPaySiteConfig config) {
        super(config);
    }

    @Override
    public void persistent(String rsp) {

    }
}
