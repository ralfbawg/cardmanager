package com.ralf.cardmanager.spider.task.epaylinks.operation;

import com.ralf.cardmanager.spider.task.epaylinks.config.EpaylinksSiteConfig;
import com.ralf.cardmanager.spider.task.epaylinks.operation.base.BaseDivvyOperation;
import org.springframework.stereotype.Service;

@Service
public class KeepSession extends BaseDivvyOperation<KeepSessionRsp> {
    public KeepSession(EpaylinksSiteConfig config) {
        super(config);
    }

    @Override
    public KeepSessionRsp persistent(String rsp) {
        return null;
    }
}

